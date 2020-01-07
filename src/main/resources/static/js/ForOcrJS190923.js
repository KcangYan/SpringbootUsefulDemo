/**
 * Created by hp on 2019/9/23.
 */
$(document).ready(function() {
            var formData = new FormData();
            $("#upload").click(function() {
                if($("#PassWord").val()==""){
                    alert("请输入密码!")
                }else {
                    if($("#InputFile").val()==""){
                        alert("请选择文件!")
                    }else {
                         formData.append("PassWord", $("#PassWord").val());
                         formData.append("File", $("#InputFile")[0].files[0]);
                         $.showLoading('上传中--')
                         $.ajax({
                              url: "/upload",
                              type: "POST",
                              data: formData,
                              cache: false,
                              processData: false,  // 默认 | 不处理数据
                              contentType: false ,// 默认 | 不设置内容类型
                              async: true, //异步请求
                              success: function (data) {
                                  $.hideLoading()
                                  if(data=="密码错误！"){
                                      alert(data)
                                      window.location.reload()
                                  }else {
                                      if(data=="上传文件请小于2M！"){
                                          alert(data)
                                          window.location.reload()
                                      }else {
                                          if(data=='请上传"jpg","png"或"jpge"格式的图片！'){
                                              alert(data)
                                          }else {
                                              if(data=='识别错误！'){
                                                  alert(data)
                                              }else {
                                                  data = JSON.parse(data)
                                                  $("#result").text(data["result"])
                                                  alert(data["msg"])
                                              }
                                          }
                                      }
                                  }
                               },
                              error: function (jqXHR, textStatus, errorThrown) {
                                 $.hideLoading()
                                  alert("上传失败！")
                              }
                          })
                    }
                }
            });
})