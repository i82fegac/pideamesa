class Plato {
	
	constructor(ko, id, nombre, pvp) {
		this.id = id
		this.nombre = nombre
		this.pvp = pvp
		this.cantidad = ko.observable(0)
		this.total = ko.observable(0)
	}
	
	recalcular() {
		this.total(this.cantidad()*this.pvp)
	}
}