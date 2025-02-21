package com.ntou.creditcard.management.application;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ntou.tool.JsonTool;
import com.ntou.tool.RegexpTool;
import lombok.Data;

@Data
public class ApplicationReq {
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
    private String eventCode				= "";//VARCHAR(10)	活動代碼
    private String cardType					= "";//VARCHAR(5)	卡別
    private String remark					= "";//VARCHAR(20)	備註

    @JsonIgnore
    private String errMsg;
    public boolean checkReq(){
        if(!RegexpTool.LENGTH_50.matcher(chName					).matches()){this.errMsg = "中文姓名"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_50.matcher(enName					).matches()){this.errMsg = "VARCHAR(50)"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.CID.matcher(cid  						).matches()){this.errMsg = "使用者身分證字號(限本國人士)"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_10.matcher(cidReissueDate			).matches()){this.errMsg = "身分證換發日期 民國yyy/mm/dd"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_10.matcher(cidReissueLocation		).matches()){this.errMsg = "身分證換發地點"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_5.matcher(cidReissueStatus			).matches()){this.errMsg = "身分證換發狀態"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_10.matcher(birthDate					).matches()){this.errMsg = "出生日期西元年月日 yyyy/mm/dd"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_2.matcher(maritalStatus				).matches()){this.errMsg = "婚姻；已婚：01、未婚：02"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_2.matcher(education					).matches()){this.errMsg = "學歷；00：無、01：國小、02：國中、03：高中、04：大學、0"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_255.matcher(currentResidentialAdd	).matches()){this.errMsg = "現居住址"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_255.matcher(residentialAdd			).matches()){this.errMsg = "戶籍地址"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_10.matcher(cellphone					).matches()){this.errMsg = "連絡手機號碼"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_100.matcher(email					).matches()){this.errMsg = "連絡電子信箱"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_100.matcher(companyName				).matches()){this.errMsg = "申請人公司名稱"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_2.matcher(companyIndustry			).matches()){this.errMsg = "申請人行業別(01：服務業、02：工業、03：金融保險業)"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_10.matcher(occupation				).matches()){this.errMsg = "申請人職業"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_10.matcher(department				).matches()){this.errMsg = "申請人部門"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_10.matcher(jobTitle					).matches()){this.errMsg = "申請人職稱"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_10.matcher(dateOfEmployment			).matches()){this.errMsg = "到職日"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_255.matcher(companyAddress			).matches()){this.errMsg = "公司地址"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_20.matcher(companyPhoneNum			).matches()){this.errMsg = "電話021234567#3654"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_5.matcher(annualIncome				).matches()){this.errMsg = "年收入/萬"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_10.matcher(eventCode					).matches()){this.errMsg = "活動代碼"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_5.matcher(cardType					).matches()){this.errMsg = "卡別"+ RegexpTool.C_INVALID_NUM_LEN;return false;}
        if(!RegexpTool.LENGTH_20.matcher(remark					).matches()){this.errMsg = "備註"+ RegexpTool.C_INVALID_NUM_LEN;return false;}

        return true;
    }

    @Override
    public String toString() {return JsonTool.format2Json(this);}
}
