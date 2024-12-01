var idLok = localStorage.getItem("lokasiId")
var dokter = localStorage.getItem("nameDokter")
var tindakan = localStorage.getItem("nameTindakan")
var spesialis = localStorage.getItem("idSpesialis")
var spesial = localStorage.getItem("spesialis")
var lokasi = ""
var mmfId = 0
var count = false
var flag = 0


$(function() {
	fetchDokter(localStorage.getItem("lokasiId"), localStorage.getItem("nameDokter"), localStorage.getItem("nameTindakan"), localStorage.getItem("idSpesialis"))
	topBar()
	console.log(localStorage.getItem("namaLokasi"), ",", "dokter :",dokter, ",", "tindakan : ",tindakan, ",","spesialis : ", spesialis)
    // console.log()
})
var halaman = 0
var halamanPerLembar = 0


const now = new Date()
const day = now.getDay()
const jamcons = Number(now.getHours())
const menitcons = Number(now.getMinutes())
const tahuncons = Number(now.getFullYear())

var jam = jamcons
var menit = menitcons
var tahun = tahuncons
var hari = ""
var lokasiId = 0
var parentId = 0
if (day === 1) {
	hari = "Monday"
}
else if (day == 2) {
	hari = "Tuesday"
}
else if (day == 3) {
	hari = "Wednesday"
}
else if (day == 4) {
	hari = "Thursday"
}
else if (day == 5) {
	hari = "Friday"
}
else if (day == 6) {
	hari = "Saturday"
}
else if (day == 0) {
	hari = "Sunday"
}

function getlokasi(dokterId, nomor) {
	$.ajax({
		url: `/api/office/schedule/doctor/${dokterId}`,
		type: 'get',
		contentType: 'application/json',
		success: function(dataSchedule) {
			console.log(dataSchedule.data)
			count = false
			var cek = false
			var lokasi = ""
			var chat = ""
			var data = 0
			for (var i = 0; i < dataSchedule.data.length; i++) {
				hari = "Monday"
				jam = 9
				menit = 20
				var jamAwal = Number((dataSchedule.data[i].mMedicalFacilitySchedule.timeScheduleStart).substring(0, 2))
				var menitAwal = Number((dataSchedule.data[i].mMedicalFacilitySchedule.timeScheduleStart).substring(3, 5))
				var jamAkhir = Number((dataSchedule.data[i].mMedicalFacilitySchedule.timeScheduleEnd).substring(0, 2))
				var menitAkhir = Number((dataSchedule.data[i].mMedicalFacilitySchedule.timeScheduleEnd).substring(3, 5))
				console.log("jam awal:",jamAwal, "menit awal:",menitAwal,"jam akhir:" ,jamAkhir,"menit akhir:", menitAkhir)
				if (dataSchedule.data[i].mMedicalFacilitySchedule.day === hari) {
					if (jam >= jamAwal && jam <= jamAkhir) {
						if (menit <= menitAkhir) {
							count = true
						} else if (jam < jamAkhir && menit >= menitAkhir) {
							count = true
						}
					}
				}
				if (dataSchedule.data[i].mMedicalFacilitySchedule.mMedicalFacility.mMedicalFacilityCategory.name == "Online") {
					cek = false
					continue
				} else if (data < 2) {
					data += 1
					lokasi += '<p class="ml-2 mb-3" style="font-size: small;"><img src="/svg/hospital.svg" alt="hospital SVG" width="30px" height="30px" class="text-warning mr-2"/> ' + dataSchedule.data[i].mMedicalFacilitySchedule.mMedicalFacility.name + ' ( ' + dataSchedule.data[i].mMedicalFacilitySchedule.mMedicalFacility.mLocation.name + ' , ' + dataSchedule.data[i].mMedicalFacilitySchedule.mMedicalFacility.mLocation.mLocation.name + ' )</p>'
				}
				console.log("data schedule: ",dataSchedule.data[i].mDoctor.mBiodata.name, flag, count)


			}
			if (cek) {
				chat = '<div class="d-flex justify-content-end text-bold mr-2 mb-2" style="font-weight: bold;inline-size: min;">Cannot Chat</div>'
			} else if (count) {
				chat = '<div class="d-flex justify-content-end text-bold mr-1 mb-2" style="inline-size: min;"><button class="btn btn-outline-primary " style="height:35px; width:100px;" onclick="chat()">Chat</button></div>'
			} else {
				chat = '<div class="d-flex justify-content-end text-bold mr-4 mb-2" style="font-weight: bold;inline-size: min;">Offline</div>'
			}
			$('#isiDataLokasi' + nomor).html(lokasi)
			$('#chat' + nomor).html(chat)

		}
	})
}

function getPengalaman(dokterId, nomor) {
	$.ajax({
		url: `/office/${dokterId}`,
		type: 'get',
		contentType: 'application/json',
		success: function(dataOffice) {
			var pengalaman = 0
			var string = ""
			for (var i = 0; i < dataOffice.length; i++) {
				/*if (dataOffice[i].mMedicalFacility.mMedicalFacilityCategory.name === "Online") {
					continue
				}*/
				var tahunAwalSebelumnya = 0
				var tahunAkhirSebelumnya = 0
				var selisihDikit = 0
				if (i > 0) {
					tahunAwalSebelumnya = Number((dataOffice[i - 1].startDate).substring(0, 4))
					if (dataOffice[i - 1].endDate == null) {
						tahunAkhirSebelumnya = tahun - 1
					} else {
						tahunAkhirSebelumnya = Number((dataOffice[i - 1].endDate).substring(0, 4))
					}
				}
				var tahunAwal = Number((dataOffice[i].startDate).substring(0, 4))
				var tahunAkhir = 0
				if (dataOffice[i].endDate == null) {
					tahunAkhir = tahun - 1
				} else {
					tahunAkhir = Number((dataOffice[i].endDate).substring(0, 4))
				}

				if (tahunAwalSebelumnya == 0) {
					pengalaman += (tahunAkhir - tahunAwal)
				} else {
					if (tahunAwal >= tahunAwalSebelumnya && tahunAwal <= tahunAkhirSebelumnya) {
						if (tahunAkhir > tahunAkhirSebelumnya) {
							selisihDikit += tahunAkhir - tahunAkhirSebelumnya
							pengalaman += selisihDikit
						}
					} else if (tahunAwal < tahunAwalSebelumnya) {
						selisihDikit = tahunAwalSebelumnya - tahunAwal
						if (tahunAkhir > tahunAkhirSebelumnya) {
							selisihDikit += tahunAkhir - tahunAkhirSebelumnya
							pengalaman += selisihDikit
						} else {
							pengalaman += selisihDikit
						}
					} else {
						pengalaman += (tahunAkhir - tahunAwal)
					}
				}
			}
			string = '<p class="ml-3" style="font-size: medium;">' + pengalaman + ' Tahun Pengalaman </p>'
			$('#pengalaman' + nomor).html(string)
		}
	})
}

function fetchDokter(lok, fullname, tndkn, spsls) {
	lokasi = lok
	console.log("lokasi: ",lokasi)
	$.ajax({
		url: `/doctor/pagingsearch?namaDokter=${fullname}&idSpesialis=${spsls}&namaTindakan=${tndkn}&lokasiId=${lok}`,
		type: 'get',
		contentType: 'application/json',
		success: function(dataDoctor) {
            console.log("data doctor1: ", dataDoctor)
			dokter = fullname
			tindakan = tndkn
			spesialis = spsls
			halaman = dataDoctor.page
			halamanPerLembar = dataDoctor.perPage
			var str = ""
			for (var i = 0; i < dataDoctor.data.length; i++) {
				countHari = false
				str += '<div class="col-6" style"width:40px;height:40px;">'
				str += '<div class="card border-primary shadow mb-3" >'
				str += '<div class="card-body" >'
				str += '<div class="row">'
				str += '<div class="col-md-8">'
				str += '<div id="rumahSakit" class="text-xs font-weight-bold text-primary text-uppercase mb-1" style="font-size: medium;">' + dataDoctor.data[i].fullName + '</div>'
				str += '<p class="ml-3" style="font-size: small; margin-bottom:0">' + dataDoctor.data[i].namaSpesialis + '</p>'
				getPengalaman(dataDoctor.data[i].dokterId, i)
				str += '<p id="pengalaman' + i + '"></p>'
				getlokasi(dataDoctor.data[i].dokterId, i)
				str += '<p class="ml-2 mb-5" style="font-size: small;" id="isiDataLokasi' + i + '"></p>'
				str += '<button class="btn btn-outline-primary form-control" style="height:35px; position: absolute; bottom: 8px; left: 16px; font-size: 18px; text-align:center; inline-size: 300px;" onclick="ganti(' + dataDoctor.data[i].dokterId + ')" >Lihat info lebih banyak</button>'
				str += '</div>'
				str += '<div class="col-sm">'
				str += '<div class="d-flex justify-content-end pt-3 pr-2 mb-4"><img class="rounded-circle" src="' + dataDoctor.data[i].fotoUrl + '" alt="person SVG" width="95px" height="95px" class="text-warning" /></div>'
				/*console.log(mmfId)*/
				str += '<div id="chat' + i + '"></div>'
				str += '<div class="d-flex justify-content-end pr-1 mb-2"><button class="btn btn-primary text-white" style="height:40px; width:100px;" onclick="buatJanji()">Buat Janji</button></div>'
				str += '</div>'
				str += '</div>'
				str += '</div>'
				str += '</div>'
				str += '</div>'

				/*$.ajax({
					url: `/biodata/${dataDoctor[i].biodataId}`,
					type: 'get',
					contentType: 'application/json',
					success: function(dataBio) {
	
					}
				})*/
			}
			$('#searchValue').hide()
			$('#btnSearch').hide()
			$('#isidata').html(str)
			pagingListSearch(dataDoctor)
		}

	})
}

function fetchDokterPaging(page, perPage) {
	if (lokasi != 0 || lokasi != null) {
		$.ajax({
			url: `/doctor/pagingsearch?namaDokter=${dokter}&idSpesialis=${spesialis}&namaTindakan=${tindakan}&lokasiId=${lokasi}&page=${page}&perPage=${perPage}`,
			type: 'get',
			contentType: 'application/json',
			success: function(dataDoctor) {
                console.log("Data doctor2 : ", dataDoctor)
				halaman = dataDoctor.page
				halamanPerLembar = dataDoctor.perPage
				var str = ""
				for (var i = 0; i < dataDoctor.data.length; i++) {
					countHari = false
					str += '<div class="col-6" style"width:40px;height:40px;">'
					str += '<div class="card border-primary shadow mb-3" >'
					str += '<div class="card-body" >'
					str += '<div class="row">'
					str += '<div class="col-md-8">'
					str += '<div id="rumahSakit" class="text-xs font-weight-bold text-primary text-uppercase mb-1" style="font-size: medium;">' + dataDoctor.data[i].fullName + '</div>'
					str += '<p class="ml-3" style="font-size: small; margin-bottom:0">' + dataDoctor.data[i].namaSpesialis + '</p>'
					getPengalaman(dataDoctor.data[i].dokterId, i)
					str += '<p id="pengalaman' + i + '"></p>'
					getlokasi(dataDoctor.data[i].dokterId, i)
					str += '<p class="ml-2 mb-5" style="font-size: small;" id="isiDataLokasi' + i + '"></p>'
					str += '<button class="btn btn-outline-primary form-control" style="height:35px; position: absolute; bottom: 8px; left: 16px; font-size: 18px; text-align:center; inline-size: 300px;" onclick="ganti(' + dataDoctor.data[i].dokterId + ')" >Lihat info lebih banyak</button>'
					str += '</div>'
					str += '<div class="col-sm">'
					str += '<div class="d-flex justify-content-end pt-3 pr-2 mb-4"><img class="rounded-circle" src="' + dataDoctor.data[i].fotoUrl + '" alt="person SVG" width="95px" height="95px" class="text-warning" /></div>'
					/*console.log(mmfId)*/
					str += '<div id="chat' + i + '"></div>'
					str += '<div class="d-flex justify-content-end pr-1 mb-2"><button class="btn btn-primary text-white" style="height:40px; width:100px;" onclick=window.location.href="/buatjanji">Buat Janji</button></div>'
					str += '</div>'
					str += '</div>'
					str += '</div>'
					str += '</div>'
					str += '</div>'

					/*$.ajax({
						url: `/biodata/${dataDoctor[i].biodataId}`,
						type: 'get',
						contentType: 'application/json',
						success: function(dataBio) {
		
						}
					})*/
				}
				$('#searchValue').hide()
				$('#btnSearch').hide()
				$('#isidata').html(str)
				pagingListSearch(dataDoctor)
			}

		})
	} else {

	}

}


function pagingListSearch(data) {
	var nextMove = halaman + 1 > data.totalPages - 1 ? data.totalPages - 1 : halaman + 1
	var prevMove = halaman - 1 < 0 ? 0 : halaman - 1
	//val = encodeURI(val)
	var str = ''
	str += '<nav aria-label="Page navigation example">'
	str += '<ul class="pagination">'
	str += '<li class="page-item"><a class="page-link" onclick=fetchDokterPaging(' + prevMove + ',' + halamanPerLembar + ')>Prev</a></li>'

	for (var i = 0; i < data.totalPages; i++) {
		if (i == data.page) {
			str += '<li class="page-item active"><a class="page-link" onclick=fetchDokterPaging(' + i + ',' + halamanPerLembar + ')>' + (i + 1) + '</a></li>'
		} else {
			str += '<li class="page-item"><a class="page-link" role onclick=fetchDokterPaging(' + i + ',' + halamanPerLembar + ')>' + (i + 1) + '</a></li>'
		}
	}

	str += '<li class="page-item"><a class="page-link" onclick=fetchDokterPaging(' + nextMove + ',' + halamanPerLembar + ')>Next</a></li>'
	str += '</ul>'
	str += '</nav>'
	$('#getHalaman').html(str)
}

function ganti(idDokter) {
	localStorage.setItem("dokterId", idDokter)
	window.location.href = "/app/doctor/detail"
}

function topBar() {
	var isi = 'Hasil pencarian berdasarkan '
	if (dokter != "") {
		isi += 'nama = ' + dokter + ', '
	}
	if (spesialis != "") {
		isi += 'Spesialis/Sub Spesialis = ' + localStorage.getItem("spesialis") + ', '
	}
	if (tindakan != "") {
		isi += 'Tindakan = ' + tindakan + ', '
	}
	if (idLok != "") {
		isi += 'Lokasi = ' + localStorage.getItem("lokasi") + ', '
	}

	var str = ""
	str += '<div class="col-7 ml-4">'
	str += '<h5>' + isi + '</h5>'
	str += '</div>'
	str += '<div class="d-flex col-4 justify-content-end mb-5 ml-5 ">'
	str += '<button class="btn btn-light btn-outline-primary" style="height: 60xp; width: 150px;" onclick="openModal()">Ulangi Pencarian</button>'
	str += '</div>'

	$('#topBar').html(str)

}
function chat(){
	window.location.href = "/chat"
}

function buatJanji(){
	window.location.href = "/buatjanji"
}
