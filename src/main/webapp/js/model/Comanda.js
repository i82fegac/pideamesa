class Comanda {
	constructor(ko) {
		this.ko = ko
		this.platos = ko.observableArray([])
		this.total = ko.observable(0)
	}
	
	addAlServidor(plato, cantidad) {
		let self = this;
		
		let info = {
			idPlato : plato.id,
			cantidad : cantidad
		}

		let data = {
			url : "platos/add/",
			type : "post",
			data : JSON.stringify(info),
			contentType : 'application/json',
			success : function() {
				plato.cantidad(plato.cantidad()+cantidad)
				plato.recalcular()
				
				let total = 0
				for (let i=0; i<self.platos().length; i++)
					total = total + self.platos()[i].total()
				
				self.total(total)
			},
			error : function(response) {
				self.error(response.responseJSON.message);
			}
		};
		$.ajax(data);
	}
		
	loadPlatos($, bar) {
		let self = this;
		let data = {
			url : "platos/load/"+bar.id,
			type : "get",
			contentType : 'application/json',
			success : function(response) {
				for (let i=0; i<response.length; i++) {
					let nombre = response[i].nombre
					let pvp = response[i].pvp
					let plato = new Plato(self.ko, nombre, pvp)
					self.platos.push(plato)	
				}
			},
			error : function(response) {
				self.error(response.responseJSON.message);
			}
		};
		$.ajax(data);
	}

}