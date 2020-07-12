package com.bili.cms.controller;


import com.bili.cms.entity.CrmBanner;
import com.bili.cms.service.CrmBannerService;
import com.bili.commonUtils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前台banner显示
 * </p>
 *
 * @author testjava
 * @since 2020-03-07
 */
@RestController
@RequestMapping("/eduCms/bannerFront")

public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    //查询所有banner
    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("list",list);
    }
}

