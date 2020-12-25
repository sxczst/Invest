package org.sxczst.invest.bean

/**
 * @Author      :sxczst
 * @Date        :Created in 2020/12/25 22:12
 * @Description : 产品实体类
 */
data class Product(
    var id: String,
    var memberNum: String,
    var minTouMoney: String,
    var money: String,
    var name: String,
    var progress: String,
    var suoDingDays: String,
    var yearRate: String
)