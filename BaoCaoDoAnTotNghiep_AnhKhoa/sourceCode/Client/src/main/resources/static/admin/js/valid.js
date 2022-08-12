
$("#validForm").validate({
	rules: {
		tenNguoiDung: {
			required: true,
			minlength: 2
		},
		soDienThoai: {
			required: true
		},
		diaChi: {
			required: true
		}
	},
	messages: {
		tenNguoiDung: {
			required: "không để trống!"
		},
		soDienThoai: {
			required: "không để trống!"
		},
		diaChi: {
			required: "không để trống!"
		}
	},

	submitHandler: function(form) {
		form.submit();
	}
});

