$(function(){
    var $validForm = $("validForm");
    if($validForm.length){
        $validForm.validate({
            rules:{
                tenNguoiDung: {
                    required: true
                },
                soDienThoai: {
                    required: true
                },
                diaChi: {
                    required: true
                }
            },
            messages: {
                tenNguoiDung:{
                    required: "không để trống!"
                },
                soDienThoai: {
                    required: "không để trống!"
                },
                diaChi: {
                    required: "không để trống!"
                }
            },
        })
    }
})