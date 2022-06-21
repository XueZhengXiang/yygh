package com.atguigu.yygh.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.yygh.cmn.listener.DictListener;
import com.atguigu.yygh.cmn.mapper.DictMapper;
import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.vo.cmn.DictEeVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lijian
 * @create 2021-04-22 15:56
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
    @Autowired
    private DictMapper dictMapper;
    //根据数据id查询子数据列表
    @Override
    @Cacheable(value = "dict",keyGenerator = "keyGenerator")  //添加缓存
    public List<Dict> findChildData(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        List<Dict> dictList = baseMapper.selectList(wrapper);
        //向list每个dict对象中设置hasChildren值
        for (Dict dict : dictList) {
            Long dictId = dict.getId();
            boolean ischild = this.ischildren(dictId);
            dict.setHasChildren(ischild);
        }
        return dictList;
    }

    //判断id下面是否有子节点
    private boolean ischildren(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        Integer count = baseMapper.selectCount(wrapper);
        return count > 0;
    }

    //导出数据字典接口
    @Override
    public void exportDictData(HttpServletResponse response) {
        //设置下载信息
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = "dict";
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        //查询数据库
        List<Dict> dictList = baseMapper.selectList(null);

        //把Dict对象转换成DictEeVo对象
        List<DictEeVo> dictVoList = new ArrayList<>(dictList.size());
        for(Dict dict :dictList){
            DictEeVo dictEeVo = new DictEeVo();
            BeanUtils.copyProperties(dict,dictEeVo);
            dictVoList.add(dictEeVo);
        }

        //调用方法进行写操作
        try {
            EasyExcel.write(response.getOutputStream(), DictEeVo.class)
                    .sheet("dict")
                    .doWrite(dictVoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //导入数据字典
    @Override
    @CacheEvict(value = "dict", allEntries=true)  //表示清空缓存中的所有内容
    public void importDictData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(),DictEeVo.class,new DictListener(dictMapper))
                    .sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //查询名称
    @Override
    public String getDictName(String dictCode, String value) {
        //如果dictCode为空,直接根据value查询
        if(StringUtils.isEmpty(dictCode)){
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("value",value);
            Dict dict = dictMapper.selectOne(queryWrapper);
            return dict.getName();
        }else{//如果dictCode不为空,根据dictCode和value查询
            //根据dictcode查询dict对象，目的是得到dict的id值，在根据此id值查询此id下的子id
            QueryWrapper<Dict> queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("dict_code",dictCode);
            Dict codeDict = dictMapper.selectOne(queryWrapper1);
            Long parent_id = codeDict.getId();
            //根据parentId和value值进行查询
            QueryWrapper<Dict> queryWrapper2 = new QueryWrapper();
            queryWrapper2.eq("parent_id",parent_id).eq("value",value);
            Dict finalDict = baseMapper.selectOne(queryWrapper2);
            return finalDict.getName();
        }
    }

    //根据dictcode查询查询子节点
    @Override
    public List<Dict> findByDictCode(String dictCode) {
        //根据dictcode获取对应的id
        QueryWrapper<Dict> queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("dict_code",dictCode);
        Dict codeDict = dictMapper.selectOne(queryWrapper1);
        //根据id获取子节点
        List<Dict> childData = this.findChildData(codeDict.getId());
        return childData;
    }


}
