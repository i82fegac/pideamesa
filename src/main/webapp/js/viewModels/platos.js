define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {

	class PlatosViewModel {
		constructor() {
			let self = this;
			
			self.comanda = ko.observable(new Comanda(ko))
			self.selectedBar = ko.observable(null)
			
			self.error = ko.observable() 
			self.position = ko.observable()
			self.radius = ko.observable(100)

			self.loadLocation(function() {
				/*self.loadBares()*/
			})
			
			self.bares=ko.observableArray([])

			// Header Config
			self.headerConfig = ko.observable({
				'view' : [],
				'viewModel' : null
			});
			moduleUtils.createView({
				'viewPath' : 'views/header.html'
			}).then(function(view) {
				self.headerConfig({
					'view' : view,
					'viewModel' : app.getHeaderModel()
				})
			})
		}
		
		connected() {
			accUtils.announce('Login page loaded.');
			document.title = "Login";
		};

		disconnected() {
			// Implement if needed
		};

		transitionCompleted() {
			// Implement if needed
		};
		
		loadBares() {
			console.log("entroLoadBares");
			var self = this;
			self.error("");
			var info = {
				latitud : 40,
				logitud : 5,
				radio : self.radius()
			};		
			var data = {
				data : JSON.stringify(info),
				//data : "latitud="+39+"&longitud="+3+"&radio="+self.radius(),
				url : "platos/getBares",
				//type : "GET",
				type : "POST",
				contentType : 'application/json',
				success : function(response) {
					console.log("recuperados");
					for (let i=0; i<response.length; i++) {
						let id = response[i].id
						let nombre = response[i].nombre
						let bar = new Bar(ko, id, nombre)
						self.bares.push(bar)	
					}
					
				},
				error : function(response) {
					//console.log("error");
					self.error("error");
				}
			};
			$.ajax(data);
			// enviar petición a algún recurso que devuelva los bares en la ubicación que tenemos en self.position()
			// y alrededor de self.radius(), que está en metros.
			// La respuesta será un array con la lista de los bares, en forma [{ id, nombre }, ... ]
			// El usuario pinchará en un bar y llamará a loadPlatos (que está en la clase Comanda, en js/model)
			// para traerse los platos de este bar 
		}
		
		loadLocation(callback) {
			let self = this
			if (navigator.geolocation) {
				navigator.geolocation.getCurrentPosition(
					(position) => {
						self.position(position)
						callback()
						let geo_options = {
								enableHighAccuracy: true, 
								maximumAge        : 60000, 
								timeout           : 60000
						};
						navigator.geolocation.watchPosition(
							function(position) {
								self.position(position)
								callback()
							},
							function() {},
							geo_options
						);
					}
				)
			} else { 
				console.log("No se puede obtener tu ubicación");
			};
		}
	
		loadBar(bar) {
			this.selectedBar(bar)
			bar.loadMenu($, this.comanda)
		}
	
	}
	
	return PlatosViewModel;
});
