define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {

	class RecoverPwdViewModel {
		constructor() {
			var self = this;
			
			self.email = ko.observable("email")
			self.message = ko.observable()
			self.error = ko.observable()
			
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

		recoverPwd() {
			var self = this;
			self.error("");
			self.message("");
			var info = {
				email : this.email()
			};
			var data = {
				//data : JSON.stringify(info),
				data : "email="+this.email(),
				url : "user/recoverPwd",
				type : "get",
				contentType : 'application/text',
				success : function(response) {
					//app.router.go( { path : "menu"} );
					self.message("Te hemos enviado tu contraseña al correo con el que estás registrado en pideamesa.com");
				},
				error : function(response) {
					self.error(response.responseJSON.message);
				}
			};
			$.ajax(data);
		}
		
			
		/*recoverPwd() {
			app.router.go( { path : "recoverPwd" } );
		}*/

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
	}

	return RecoverPwdViewModel;
});