
var value = ""
var idLokasi = 0
var nameDoktor = ""
var spesialisId = 0
var tindakanId = 0
var spesial = ""
var namalokasi = ""

function oper(val) {
	value = val
}


function getSpesialisName(spesialisId) {
	$.ajax({
		url: `/dokter/spesialis/${spesialisId}`,
		type: 'get',
		contentType: 'application/json',
		success: function(dataSpesialis) {
			spesial = dataSpesialis.name
		}
	})
}

function openModal() {
	$.ajax({
		url: '/dokter/lokasi',
		type: 'get',
		contentType: 'application/json',
		success: function(dataLokasi) {
			var nameLokasi = ""
			var str = '<form>'
			str += '<h5>Masukkan Minimal 1 Kata Kunci Untuk Pencarian Dokter Anda</h5>'
			str += '<p></p>'
			str += '<div class="form-group">'
			str += '<label>Lokasi</label>'
			str += '<select class = "custom-select form-control" id="lokasiId">'
			str += '<option value="">---Pilih---</option>'
			for (var i = 0; i < dataLokasi.length; i++) {
				for (var j = 0; j < dataLokasi.length; j++) {
					if (dataLokasi[j].name == dataLokasi[i].name) {
						cek = 1
					}
				}
				if (dataLokasi[i].parentId == null && cek > 0) {
					str += '<option value="' + dataLokasi[i].id + '">' + dataLokasi[i].name + '</option>'
					nameLokasi = dataLokasi[i].name
					cek = 0
				}
			}
			str += '</select>'
			str += '</div>'
			str += '<br>'
			str += '<div class="form-group">'
			str += '<label>Nama Dokter</label>'
			str += '<input type="text" class="form-control" value="' + value + '" id="namadokter" oninput="oper(this.value)">'
			str += '</div>'
			str += '<br>'


			$.ajax({
				url: '/dokter/spesialis',
				type: 'get',
				contentType: 'application/json',
				success: function(dataSpesialis) {

					str += '<div class="form-group">'
					str += '<label>Spesialisasi / Sub-Spesialisasi</label>'
					str += '<select class = "custom-select form-control" id="spesialis">'
					str += '<option value="">---Pilih---</option>'
					for (var i = 0; i < dataSpesialis.length; i++) {
						for (var j = 0; j < dataSpesialis.length; j++) {
							if (dataSpesialis[j].name == dataSpesialis[i].name) {
								cek = 1
							}
						}
						if (dataSpesialis[i].parentId == null && cek > 0) {
							str += '<option value="' + dataSpesialis[i].id + '">' + dataSpesialis[i].name + '</option>'
							cek = 0
						}
					}
					str += '</select>'
					str += '<div class="alert alert-danger form-group" role="alert"></div>'
					str += '</div>'
					str += '<br>'
					str += '<div class="form-group">'
					$.ajax({
						url: '/treatment/all',
						type: 'get',
						contentType: 'application/json',
						success: function(dataTindakan) {
							str += '<label>Tindakan Medis</label>'
							str += '<select class = "custom-select form-control" id="tindakan">'
							str += '<option value="">---Pilih---</option>'
							for (var i = 0; i < dataTindakan.length; i++) {
								str += '<option value="' + dataTindakan[i] + '">' + dataTindakan[i] + '</option>'
							}
							str += '</select>'
							str += '</div>'
							str += '<br>'
							str += '</form>'

							$('.modal-title').html("Cari Dokter")
							$('.modal-body').html(str)
							$('#btnSubmit').html("Cari").off('click').on('click', function() { change(nameLokasi) })
							$('.alert').hide()
							$('.modal-footer').show()
							$('#btnCancel').html("Atur Ulang").addClass("btn-light btn-outline-primary")
							$('#btnCancel').off('click').on('click', close)
							$('#modal').modal('show')
						}
					})
				}
			})

		}
	})

}

function change() {
	value = value.trim()
	idLokasi = $('#lokasiId').val().trim()
	spesialisId = $('#spesialis').val().trim()
	tindakanId = $('#tindakan').val().trim()
	if (spesialisId == "") {
		$('.alert').html(' Pilihan Spesialisasi Harus Di isi *')
		$('.alert').show()
	} else {
		$.ajax({
			url: `/dokter/spesialis/${spesialisId}`,
			type: 'get',
			contentType: 'application/json',
			success: function(dataSpesialis) {
				spesial = dataSpesialis.name
				console.log(idLokasi)
				if (idLokasi == null || idLokasi == 0 || idLokasi == "") {

					localStorage.setItem("lokasiId", idLokasi)
					localStorage.setItem("nameDokter", value)
					localStorage.setItem("nameTindakan", tindakanId)
					localStorage.setItem("idSpesialis", spesialisId)
					localStorage.setItem("spesialis", spesial)
					localStorage.setItem("lokasi", namalokasi)
					console.log(spesial)

					window.location.href = "/dokter/caridokter"


				} else {
					$.ajax({
						url: `/dokter/lokasi/${idLokasi}`,
						type: 'get',
						contentType: 'application/json',
						success: function(dataLokasi) {
							namalokasi = dataLokasi.name.trim()
							localStorage.setItem("lokasiId", idLokasi)
							localStorage.setItem("nameDokter", value)
							localStorage.setItem("nameTindakan", tindakanId)
							localStorage.setItem("idSpesialis", spesialisId)
							localStorage.setItem("spesialis", spesial.trim())
							localStorage.setItem("lokasi", namalokasi)
							console.log(spesial)

							window.location.href = "/dokter/caridokter"
						}
					})
				}
			}
		})
	}

}

function close() {
	$('#modal').modal('toggle')
}


