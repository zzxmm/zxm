package com.shouzan.back.entity.card;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/12/28 11:41
 * @Description:  公众号卡卷 基本信息
 */
@Data
public class BaseInfo implements Serializable {

    private static final long serialVersionUID = 5041731339960487079L;

    //卡券的商户logo
    @NotNull(message = "商户LOGO不能为空")
    private String logo_url;

    //商户名字  字数上限为12个汉字
    @NotNull(message = "商户名称不能为空")
    @Length(max = 12 , message = "商户名字上限12个字")
    private String brand_name;

    //码型： "CODE_TYPE_TEXT"文 本 ； "CODE_TYPE_BARCODE"一维码 "CODE_TYPE_QRCODE"二维码 "CODE_TYPE_ONLY_QRCODE",二维码无code显示； "CODE_TYPE_ONLY_BARCODE",一维码无code显示；CODE_TYPE_NONE， 不显示code和条形码类型
    @NotNull(message = "商品码型不能为空")
    private String code_type;

    // 卡卷名   字数上限为9个汉字
    @NotNull(message = "卡卷名称不能为空")
    @Length(max = 9 , message = "卡卷名字上限9个字")
    private String title;

    //券颜色。按色彩规范标注填写Color010-Color100。
    @NotNull(message = "卡卷颜色不能为空")
    private String color;

    // 卡券使用提醒，字数上限为16个汉字。
    @NotNull(message = "卡卷使用提醒不能为空")
    @Length(max = 16 , message = "卡卷使用提醒上限16个字")
    private String notice;

    //客服电话。
    private String service_phone;

    //卡券使用说明，字数上限为1024个汉字。
    @NotNull(message = "卡卷使用说明不能为空")
    @Length(max = 1024 , message = "卡卷使用说明上限1024个字")
    private String description;

    //使用日期，有效期的信息。
    @Valid
    @NotNull(message = "使用日期不能为空")
    private DateInfo date_info;

    //商品信息
    @Valid
    @NotNull(message = "卡卷库存不能为空")
    private Sku sku;

    //每人可核销的数量限制,不填写默认为50。
    private Integer use_limit;

    //每人可领券的数量限制,不填写默认为50。
    private Integer get_limit;

    //是否自定义Code码 。填写true或false，默认为false。 通常自有优惠码系统的开发者选择 自定义Code码，并在卡券投放时带入 Code码，详情见 是否自定义Code码
    private boolean use_custom_code;

    //是否指定用户领取，填写true或false 。默认为false。通常指定特殊用户群体 投放卡券或防止刷券时选择指定用户领取。
    private boolean bind_openid;

    //卡券领取页面是否可分享。
    private boolean can_share;

    //卡券是否可转赠。
    private boolean can_give_friend;

    //门店位置poiid。 调用 POI门店管理接 口 获取门店位置poiid。具备线下门店 的商户为必填。
    private List<Integer> location_id_list;

    //卡券顶部居中的按钮，仅在卡券状 态正常(可以核销)时显示
    private String center_title;

    //显示在入口下方的提示语 ，仅在卡券状态正常(可以核销)时显示。
    private String center_sub_title;

    //顶部居中的url ，仅在卡券状态正常(可以核销)时显示。
    private String center_url;

    //自定义跳转外链的入口名字。
    private String custom_url_name;

    //自定义跳转的URL。
    private String custom_url;

    //显示在入口右侧的提示语。
    private String custom_url_sub_title;

    //营销场景的自定义入口名称。
    private String promotion_url_name;

    //入口跳转外链的地址链接。
    private String promotion_url;

    //显示在营销入口右侧的提示语。
    private String promotion_url_sub_title;

    //卡券跳转的小程序的user_name，仅可跳转该 公众号绑定的小程序 。
    private String promotion_app_brand_user_name;

    //卡券跳转的小程序的path
    private String promotion_app_brand_pass;

    //填入 GET_CUSTOM_CODE_MODE_DEPOSIT 表示该卡券为预存code模式卡券， 须导入超过库存数目的自定义code后方可投放， 填入该字段后，quantity字段须为0,须导入code 后再增加库存
    private String get_custom_code_mode;

    //设置本卡券支持全部门店，与location_id_list互斥
    private String use_all_locations;

    //卡券跳转的小程序的user_name，仅可跳转该 公众号绑定的小程序 。
    private String custom_app_brand_user_name;

    //	卡券跳转的小程序的path
    private String custom_app_brand_pass;

    //	卡卷ID
    @JSONField(serialize=false)
    private Integer cardId;
}
