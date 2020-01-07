//适用数据库：阿里云mongo 用户名smallCalc 密码123456 使用集合danjia
$(document).ready(function () {
    //按钮响应函数
    $('#button_1').click(function () {
        $("#p_input_name").text("纸皮：");
    });
    $('#button_2').click(function () {
        $("#p_input_name").text("书本：");
    });
    $('#button_3').click(function () {
        $("#p_input_name").text("报纸：");
    });
    $('#button_4').click(function () {
        $("#p_input_name").text("水泥袋：");
    });
    $('#button_5').click(function () {
        $("#p_input_name").text("衣服：");
    });
    $('#button_6').click(function () {
        $("#p_input_name").text("泡沫：");
    });
    $('#button_7').click(function () {
        $("#p_input_name").text("白料：");
    });
    $('#button_8').click(function () {
        $("#p_input_name").text("扣板：");
    });
    $('#button_9').click(function () {
        $("#p_input_name").text("红料：");
    });
    $('#button_10').click(function () {
        $("#p_input_name").text("黑蓝子：");
    });
    $('#button_11').click(function () {
        $("#p_input_name").text("洗衣机料：");
    });
    $('#button_12').click(function () {
        $("#p_input_name").text("好料：");
    });
    $('#button_13').click(function () {
        $("#p_input_name").text("有机料：");
    });
    $('#button_14').click(function () {
        $("#p_input_name").text("铁罐：");
    });
    $('#button_15').click(function () {
        $("#p_input_name").text("压块铁：");
    });
    $('#button_16').click(function () {
        $("#p_input_name").text("铁栏杆：");
    });
    $('#button_17').click(function () {
        $("#p_input_name").text("二等铁：");
    });
    $('#button_18').click(function () {
        $("#p_input_name").text("好铁加工：");
    });
    $('#button_19').click(function () {
        $("#p_input_name").text("好铁：");
    });
    $('#button_20').click(function () {
        $("#p_input_name").text("不锈钢：");
    });
    $('#button_21').click(function () {
        $("#p_input_name").text("好不锈钢：");
    });
    $('#button_22').click(function () {
        $("#p_input_name").text("铝盘：");
    });
    $('#button_23').click(function () {
        $("#p_input_name").text("带铁铝线：");
    });
    $('#button_24').click(function () {
        $("#p_input_name").text("铝罐：");
    });
    $('#button_25').click(function () {
        $("#p_input_name").text("有塑铝条：");
    });
    $('#button_26').click(function () {
        $("#p_input_name").text("铝合金：");
    });
    $('#button_27').click(function () {
        $("#p_input_name").text("净铝线：");
    });
    $('#button_28').click(function () {
        $("#p_input_name").text("杂线：");
    });
    $('#button_29').click(function () {
        $("#p_input_name").text("网线：");
    });
    $('#button_30').click(function () {
        $("#p_input_name").text("黑闭路线：");
    });
    $('#button_31').click(function () {
        $("#p_input_name").text("双皮铜线：");
    });
    $('#button_32').click(function () {
        $("#p_input_name").text("单皮铜线：");
    });
    $('#button_33').click(function () {
        $("#p_input_name").text("黄铜：");
    });
    $('#button_34').click(function () {
        $("#p_input_name").text("漆包线：");
    });
    $('#button_35').click(function () {
        $("#p_input_name").text("光亮铜：");
    });
    $('#button_36').click(function () {
        $("#p_input_name").text("杂电机：");
    });
    $('#button_37').click(function () {
        $("#p_input_name").text("电动机：");
    });
    $('#button_38').click(function () {
        $("#p_input_name").text("铝水泵：");
    });
    $('#button_39').click(function () {
        $("#p_input_name").text("马达：");
    });
    $('#button_40').click(function () {
        $("#p_input_name").text("其他(+1)：");
    });
    $('#button_41').click(function () {
        $("#p_input_name").text("其他(-1)：");
    });
    //获取输入
    var sum_price = new Array();
    $('#people_id_button').click(function () {
        var bianhao = $("#people_id").val();
        var zhongl = $("#zhongliang_id").val();
        var leibie = $("#p_input_name").text();
        var re = /^[0-9]+.?[0-9]*$/;
        if(leibie=='重量：'){
            alert('请选择种类！')
        }else {
            if(!re.test(zhongl)||bianhao==""){
            if(!re.test(zhongl)){
                alert("请输入数字！")
            }
            if(bianhao==""){
                alert("请输入编号！")
            }
        }
            else {
            $.post("/index", {"type_name":leibie},function (data,status) {
            if($("#"+String(bianhao)).length > 0){
            var old_bianhao_text = $("#"+String(bianhao)).text();
            old_bianhao_text = old_bianhao_text.replace(/; 总价：[0-9]*.[0-9]*/i,"");
            sum_price[String(bianhao)] = sum_price[String(bianhao)]+parseFloat(zhongl)*parseFloat(data)
            sum_price[String(bianhao)] = parseFloat(sum_price[String(bianhao)].toFixed(2))
            old_bianhao_text = old_bianhao_text + " + " + String(leibie) + String(zhongl) + "斤"+"; "+"总价："+String(sum_price[String(bianhao)]);
            $("#"+String(bianhao)).text(old_bianhao_text);
            $("#p_input_name").text("重量：");
            $("#zhongliang_id").val("");
        }
            else {
            sum_price[String(bianhao)] = parseFloat(zhongl)*parseFloat(data)
            sum_price[String(bianhao)] = parseFloat(sum_price[String(bianhao)].toFixed(2))
            var text = "<p"+" "+"id="+String(bianhao)+">人员"+String(bianhao)+"：  ";
            var text_last = "</p>";
            text = text + String(leibie)  + String(zhongl) + "斤" + "; "+"总价："+String(sum_price[String(bianhao)])+ text_last;
            $("#add_text").append(text);
            $("#p_input_name").text("重量：");
            $("#zhongliang_id").val("");
        }
        });
        }
        }
    });
    //删除
    $('#delete_button_id').click(function () {
        var need_to_deleteID = $("#delete_id").val();
        $("#delete_id").val("");
        $("#"+String(need_to_deleteID)).remove();
        delete sum_price[String(bianhao)];
    });
    //页面刷新或关闭反馈
    window.onbeforeunload=function (){
            $.get("/other");
        };
});
