package edu.czjt.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import edu.czjt.reggie.common.R;
import edu.czjt.reggie.entity.Orders;
import edu.czjt.reggie.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    private LambdaQueryWrapper<Orders> q;


    /**
     * 用户下单
     *
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        log.info("订单数据：{}", orders);
        orderService.submit(orders);
        return R.success("下单成功");
    }

    /**
     * 再来一单
     *
     * @param orders
     * @return
     */
    @PutMapping("/again")
    public R<String> submitAgain(@RequestBody Orders orders) {
        log.info("再来一单的订单ID:{}", orders);

        orderService.submitAgain(orders);

        return R.success("下单成功");
    }

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @param number
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("page")
    public R<Page> page(int page, int pageSize, String number, String beginTime, String endTime) {
        Page<Orders> pageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        // 根据订单号查询，可以为空
        wrapper.eq(StringUtils.isNotEmpty(number), Orders::getNumber, number).orderByDesc(Orders::getOrderTime);
        // 根据起始时间查询，可以为空
        if (StringUtils.isNotEmpty(beginTime)) {
            wrapper.gt(Orders::getOrderTime, beginTime);
        }
        // 根据截止时间查询，可以为空
        if (StringUtils.isNotEmpty(endTime)) {
            wrapper.lt(Orders::getOrderTime, endTime);
        }
        // 分页查询
        orderService.page(pageInfo, wrapper);

        return R.success(pageInfo);
    }

    /**
     * 更新订单状态
     *
     * @param orders
     * @return
     */
    @PutMapping()
    public R<String> updateOrder(@RequestBody Orders orders) {

        orderService.updateById(orders);

        return R.success("修改订单状态成功");
    }

    /**
     * 弃用
     *
     * @param page
     * @param pageSize
     * @return
     * @deprecated 该方法已经弃用，请使用 {@link OrderController#page(int, int, String, String, String)} 方法
     */
    @GetMapping("/userPage")
    public R<Orders> userPage(int page, int pageSize) {
        log.info("获取订单page{},pageSize{}", page, pageSize);
        // TODO 该功能，可以通过Page实现，所以不需要实现。
        Orders orders = new Orders();

        return R.success(orders);
    }
}
