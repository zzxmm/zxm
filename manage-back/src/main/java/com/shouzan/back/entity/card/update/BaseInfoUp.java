package com.shouzan.back.entity.card.update;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/12/28 11:41
 * @Description: 卡卷基本信息 修改用
 */
@Data
public class BaseInfoUp implements Serializable {

    private static final long serialVersionUID = -5606110402324669442L;

    //卡券的商户logo
    private String logo_url;

    //码型： "CODE_TYPE_TEXT"文 本 ； "CODE_TYPE_BARCODE"一维码 "CODE_TYPE_QRCODE"二维码 "CODE_TYPE_ONLY_QRCODE",二维码无code显示； "CODE_TYPE_ONLY_BARCODE",一维码无code显示；CODE_TYPE_NONE， 不显示code和条形码类型
    private String code_type;

    // 卡卷名   字数上限为9个汉字
    @Length(max = 9 , message = "卡卷名字上限9个字")
    private String title;

    //券颜色。按色彩规范标注填写Color010-Color100。
    private String color;

    // 卡券使用提醒，字数上限为16个汉字。
    @Length(max = 16 , message = "卡卷使用提醒上限16个字")
    private String notice;

    //客服电话。
    private String service_phone;

    //卡券使用说明，字数上限为1024个汉字。
    @Length(max = 1024 , message = "卡卷使用说明上限1024个字")
    private String description;

    //卡券使用说明，字数上限为1024个汉字。
    private DateInfoUp date_info;

    //每人可领券的数量限制,不填写默认为50。
    private Integer get_limit;

    //卡券领取页面是否可分享。
    private boolean can_share;

    //卡券是否可转赠。
    private boolean can_give_friend;

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

    //门店位置poiid。 调用 POI门店管理接 口 获取门店位置poiid。具备线下门店 的商户为必填。
    private List<Integer> location_id_list;

}
