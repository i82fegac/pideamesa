class Bar {
	constructor(ko, id, nombre) {
		this.ko = ko
		this.id = id
		this.nombre = nombre
	}

	loadMenu($, comanda) {
		let self = this;
		let data = {
			url : "platos/load/" + this.id,
			type : "get",
			contentType : 'application/json',
			success : function(response) {
				for (let i=0; i<response.length; i++) {
					let id = response[i].id
					let nombre = response[i].nombre
					let pvp = response[i].pvp
					let plato = new Plato(self.ko, id, nombre, pvp)
					comanda().platos.push(plato)	
				}
			},
			error : function(response) {
				self.viewModel.error(response.responseJSON.message);
			}
		};
		$.ajax(data);
	}
}