package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.entity.BusiDeptTitle;
import com.xiaoyu.lingdian.entity.BusiUserTitle;
import com.xiaoyu.lingdian.service.BusiDeptTitleService;
import com.xiaoyu.lingdian.service.BusiUserTitleService;
import com.xiaoyu.lingdian.vo.BusiDeptTitleVO;
import com.xiaoyu.lingdian.vo.BusiUserTitleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;

import com.xiaoyu.lingdian.service.BusiDeptService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiDept;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.BusiDeptVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 部门表
 */
@Controller
@RequestMapping(value = "/busiDept")
@Api(value = "busiDept", description = "部门相关操作")
public class BusiDeptController extends BaseController {

    /**
     * 部门表
     */
    @Autowired
    private BusiDeptService busiDeptService;

    /**
     * 个人称号记录表
     */
    @Autowired
    private BusiUserTitleService busiUserTitleService;

    /**
     * 部门称号记录表
     */
    @Autowired
    private BusiDeptTitleService busiDeptTitleService;

    /**
     * 添加
     *
     * @param bsdetName  部门名称
     * @return
     */
    @ApiOperation(value = "添加", httpMethod = "POST", notes = "添加")
    @RequestMapping(value = "/add/busiDept", method = RequestMethod.POST)
    public void addBusiDept(
            @ApiParam(value = "部门名称", required = true) @RequestParam(value = "bsdetName", required = true) String bsdetName,
            HttpServletResponse response) {
        logger.info("[BusiDeptController]:begin addBusiDept");
        BusiDept busiDept = new BusiDept();
        String uuid = RandomUtil.generateString(16);
        busiDept.setBsdetUuid(uuid);
        busiDept.setBsdetName(bsdetName);
        busiDept.setBsdetCdate(new Date());
        busiDept.setBsdetUdate(new Date());
        busiDeptService.insertBusiDept(busiDept);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "新增成功!"), response);
        logger.info("[BusiDeptController]:end addBusiDept");
    }

    /**
     * 修改
     *
     * @param bsdetUuid  标识UUID
     * @param bsdetName  部门名称
     * @return
     */
    @RequestMapping(value = "/update/busiDept", method = RequestMethod.POST)
    @ApiOperation(value = "修改", httpMethod = "POST", notes = "修改")
    public void updateBusiDept(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsdetUuid", required = true) String bsdetUuid,
            @ApiParam(value = "部门名称", required = true) @RequestParam(value = "bsdetName", required = true) String bsdetName,
            HttpServletResponse response) {
        logger.info("[BusiDeptController]:begin updateBusiDept");
        BusiDept busiDept = new BusiDept();
        busiDept.setBsdetUuid(bsdetUuid);
        busiDept.setBsdetName(bsdetName);
        busiDept.setBsdetUdate(new Date());
        busiDeptService.updateBusiDept(busiDept);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"), response);
        logger.info("[BusiDeptController]:end updateBusiDept");
    }

    /**
     * 删除
     *
     * @param bsdetUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "删除", httpMethod = "POST", notes = "删除")
    @RequestMapping(value = "/delete/one", method = RequestMethod.POST)
    public void deleteBusiDept(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsdetUuid", required = true) String bsdetUuid,
            HttpServletResponse response) {
        logger.info("[BusiDeptController]:begin deleteBusiDept");
        BusiDept busiDept = new BusiDept();
        busiDept.setBsdetUuid(bsdetUuid);

        busiDeptService.deleteBusiDept(busiDept);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功!"), response);
        logger.info("[BusiDeptController]:end deleteBusiDept");
    }

    /**
     * 批量删除
     *
     * @param bsdetUuids UUID集合
     * @return
     */
    @ApiOperation(value = "批量删除", httpMethod = "POST", notes = "批量删除")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    public void deleteBatchBusiDept(
            @ApiParam(value = "部门标识集合(|拼接)", required = true) @RequestParam(value = "bsdetUuids", required = true) String bsdetUuids,
            HttpServletResponse response) {
        logger.info("[BusiDeptController]:begin deleteBatchBusiDept");
        String[] uuids = bsdetUuids.split("\\|");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < uuids.length; i++) {
            list.add(uuids[i]);
        }
        if (list.size() == 0) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
            return;
        }
        busiDeptService.deleteBatchByIds(list);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "批量删除成功!"), response);
        logger.info("[BusiDeptController]:end deleteBatchBusiDept");
    }

    /**
     * 获取单个
     *
     * @param bsdetUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "获取单个", httpMethod = "POST", notes = "获取单个", response = BusiDeptVO.class)
    @RequestMapping(value = "/views", method = RequestMethod.POST)
    public void viewsBusiDept(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsdetUuid", required = true) String bsdetUuid,
            HttpServletResponse response) {
        logger.info("[BusiDeptController]:begin viewsBusiDept");
        BusiDept busiDept = new BusiDept();
        busiDept.setBsdetUuid(bsdetUuid);
        busiDept = busiDeptService.getBusiDept(busiDept);
        if (null == busiDept) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "部门不存在!"), response);
            logger.info("[BusiDeptController]:end viewsBusiDept");
            return;
        }

        BusiDeptVO busiDeptVO = new BusiDeptVO();
        busiDeptVO.convertPOToVO(busiDept);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", busiDeptVO), response);
        logger.info("[BusiDeptController]:end viewsBusiDept");
    }

    /**
     * 获取所有部门列表<List>
     *
     * @return
     */
    @ApiOperation(value = "获取所有部门列表", httpMethod = "POST", notes = "获取部门列表", response = BusiDeptVO.class)
    @RequestMapping(value = "/find/all", method = RequestMethod.POST)
    public void findBusiDeptList(
            HttpServletResponse response) {
        logger.info("[BusiDeptController]:begin findBusiDeptList");
        List<BusiDept> lists = busiDeptService.findBusiDeptList();
        List<BusiDeptVO> vos = new ArrayList<BusiDeptVO>();
        BusiDeptVO vo;
        for (BusiDept busiDept : lists) {
            vo = new BusiDeptVO();
            vo.convertPOToVO(busiDept);
            vos.add(vo);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "list列表获取成功!", vos), response);
        logger.info("[BusiDeptController]:end findBusiDeptList");
    }

    /**
     * 获取分页列表<Page>
     *
     * @param bsdetName  部门名称
     * @param pageNum    页码
     * @param pageSize   页数
     * @return
     */
    @ApiOperation(value = "获取部门分页列表", httpMethod = "POST", notes = "获取部门分页列表", response = BusiDeptVO.class)
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.POST)
    public void findBusiDeptPage(
            @ApiParam(value = "部门名称", required = false) @RequestParam(value = "bsdetName", required = false) String bsdetName,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[BusiDeptController]:begin findBusiDeptPage");
        BusiDept busiDept = new BusiDept();
        busiDept.setBsdetName(bsdetName);
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 15;
        }
        Page<BusiDept> page = busiDeptService.findBusiDeptPage(busiDept, pageNum, pageSize);
        Page<BusiDeptVO> pageVO = new Page<BusiDeptVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
        List<BusiDeptVO> vos = new ArrayList<BusiDeptVO>();
        BusiDeptVO vo;
        for (BusiDept busiDeptPO : page.getResult()) {
            vo = new BusiDeptVO();
            vo.convertPOToVO(busiDeptPO);
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[BusiDeptController]:end findBusiDeptPage");
    }

    /**
     * 获取个人称号分页列表<Page>
     *
     * @param name 用户昵称
     * @param pageNum    页码
     * @param pageSize   页数
     * @return
     */
    @ApiOperation(value = "获取个人称号记录分页列表", httpMethod = "POST", notes = "获取个人称号记录分页列表", response = BusiUserTitleVO.class)
    @RequestMapping(value = "/user/title/find/by/cnd", method = RequestMethod.POST)
    public void findBusiUserTitlePage(
            @ApiParam(value = "用户昵称", required = false) @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[BusiDeptController]:begin findBusiUserTitlePage");
        BusiUserTitle busiUserTitle = new BusiUserTitle();
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 15;
        }
        Page<BusiUserTitle> page = busiUserTitleService.findBusiUserTitlePage(busiUserTitle, name, pageNum, pageSize);
        Page<BusiUserTitleVO> pageVO = new Page<BusiUserTitleVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
        List<BusiUserTitleVO> vos = new ArrayList<BusiUserTitleVO>();
        BusiUserTitleVO vo;
        for (BusiUserTitle busiUserTitlePO : page.getResult()) {
            vo = new BusiUserTitleVO();
            vo.convertPOToVO(busiUserTitlePO);
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[BusiDeptController]:end findBusiUserTitlePage");
    }

    /**
     * 获取部门称号分页列表<Page>
     *
     * @param name 部门名称
     * @param pageNum 页码
     * @param pageSize 页数
     * @return
     */
    @ApiOperation(value="获取部门称号记录分页列表", httpMethod = "POST", notes = "获取部门称号记录分页列表", response = BusiDeptTitleVO.class)
    @RequestMapping(value="/dept/title/find/by/cnd", method=RequestMethod.POST)
    public void findBusiDeptTitlePage (
            @ApiParam(value="部门名称", required = false) @RequestParam(value="name", required = false) String name,
            @ApiParam(value="第几页", required = false) @RequestParam(value="pageNum", required = false) Integer pageNum,
            @ApiParam(value="每页数量", required = false) @RequestParam(value="pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[BusiDeptController]:begin findBusiDeptTitlePage");
        BusiDeptTitle busiDeptTitle = new BusiDeptTitle();
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 15;
        }
        Page<BusiDeptTitle> page = busiDeptTitleService.findBusiDeptTitlePage(busiDeptTitle, name, pageNum, pageSize);
        Page<BusiDeptTitleVO> pageVO = new Page<BusiDeptTitleVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
        List<BusiDeptTitleVO> vos = new ArrayList<BusiDeptTitleVO>();
        BusiDeptTitleVO vo;
        for (BusiDeptTitle busiDeptTitlePO : page.getResult()) {
            vo = new BusiDeptTitleVO();
            vo.convertPOToVO(busiDeptTitlePO);
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO),response);
        logger.info("[BusiDeptController]:end findBusiDeptTitlePage");
    }
}