package com.ntou.db.cuscredit;

import com.fasterxml.jackson.annotation.JsonValue;
import com.ntou.tool.JsonTool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuscreditVO {

    @AllArgsConstructor
    public enum CardApprovalStatus{
        PROCESS       ("00" , "處理中")
        , PASS        ("01" , "審核通過")
        , NOTPASS     ("02" , "審核不通過")
        ;
        private final String value;
        private final String msg;

        @JsonValue
        public String getValue() {return value;}
        public String getMsg() {return msg;}

        public static CardApprovalStatus find(String val) {
            return Arrays.stream(values())
                    .filter(predicate
                        -> predicate.value.equals(val)
                        || predicate.msg.equals(val))
                    .findFirst()
                    .orElse(null);
        }
    }
    @AllArgsConstructor
    public enum ActivationRecord{
        OPEN       ("00" , "已開卡")
        , NOTOPEN        ("01" , "未開卡")
        ;
        private final String value;
        private final String msg;

        @JsonValue
        public String getValue() {return value;}
        public String getMsg() {return msg;}

        public static ActivationRecord find(String val) {
            return Arrays.stream(values())
                    .filter(predicate
                            -> predicate.value.equals(val)
                            || predicate.msg.equals(val))
                    .findFirst()
                    .orElse(null);
        }
    }
    @AllArgsConstructor
    public enum STATUS{
        OK       ("00" , "正常")
        , CXL        ("01" , "註銷")
        ;
        private final String value;
        private final String msg;

        @JsonValue
        public String getValue() {return value;}
        public String getMsg() {return msg;}

        public static STATUS find(String val) {
            return Arrays.stream(values())
                    .filter(predicate
                            -> predicate.value.equals(val)
                            || predicate.msg.equals(val))
                    .findFirst()
                    .orElse(null);
        }
    }

    private String chName					= "";//VARCHAR(50)	中文姓名
    private String enName					= "";//VARCHAR(50)	VARCHAR(50)
    private String cid  					= "";//VARCHAR(20)	使用者身分證字號(限本國人士)
    private String cidReissueDate			= "";//VARCHAR(10)	身分證換發日期 民國yyy/mm/dd
    private String cidReissueLocation		= "";//VARCHAR(10)	身分證換發地點
    private String cidReissueStatus			= "";//VARCHAR(5)	身分證換發狀態
    private String birthDate				= "";//VARCHAR(10)	出生日期西元年月日 yyyy/mm/dd
    private String maritalStatus			= "";//VARCHAR(2)	婚姻；已婚：01、未婚：02
    private String education				= "";//VARCHAR(2)	學歷；00：無、01：國小、02：國中、03：高中、04：大學、05：碩士、06：博士
    private String currentResidentialAdd	= "";//VARCHAR(255)	現居住址
    private String residentialAdd			= "";//VARCHAR(255)	戶籍地址
    private String cellphone				= "";//VARCHAR(10)	連絡手機號碼
    private String email					= "";//VARCHAR(100)	連絡電子信箱
    private String companyName				= "";//VARCHAR(100)	申請人公司名稱
    private String companyIndustry			= "";//VARCHAR(2)	申請人行業別(01：服務業、02：工業、03：金融保險業)
    private String occupation				= "";//VARCHAR(10)	申請人職業
    private String department				= "";//VARCHAR(10)	申請人部門
    private String jobTitle				    = "";//VARCHAR(10)	申請人職稱
    private String dateOfEmployment		    = "";//VARCHAR(10)	到職日
    private String companyAddress			= "";//VARCHAR(255)	公司地址
    private String companyPhoneNum			= "";//VARCHAR(20)	電話021234567#3654
    private String annualIncome				= "";//VARCHAR(5)	年收入/萬
    private String cardApprovalStatus		= "";//VARCHAR(2)	信用卡審核狀態；00：處理中、01：審核過、02：審核不通過
    private String ApplyRemark				= "";//VARCHAR(20)	信用卡申請狀態備註，如審核不通過的原因
    private String activationRecord			= "";//VARCHAR(2)	信用卡開卡紀錄(00：未開卡、01：已開卡)
    private String eventCode				= "";//VARCHAR(10)	活動代碼
    private String regidate 				= "";//VARCHAR(23)	信用卡銀行通過(核發)時間yyyy/MM/dd HH:mm:ss.SSS
    private String issuing_bank 			= "";//VARCHAR(15)	核卡銀行(swiftCode)
    private String cardNum					= "";//VARCHAR(20)	信用卡號碼
    private String securityCode				= "";//VARCHAR(5)	信用卡安全碼
    private String status					= "";//VARCHAR(2)	信用卡狀態(00：正常,99：註銷)
    private String cardType					= "";//VARCHAR(5)	卡別
    private String remark					= "";//VARCHAR(20)	備註

    public static String encodeFormSQL(String s) {return s != null && !s.isEmpty() ? s : "";}

    @Override
    public String toString() {return JsonTool.format2Json(this);}
}
