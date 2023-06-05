define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {

	class resetPwdViewModel {
		constructor() {
			let self = this
			self.pwd1 = ko.observable()
			self.pwd2 = ko.observable()
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
			
			const urlParams = new URLSearchParams(window.location.search)
			let tokenId = urlParams.get("tokenId")
			sessionStorage.tokenId = tokenId
		}

		connected() {
			accUtils.announce('Menu page loaded.');
			document.title = "Menu";
		};
		
		

		disconnected() {
			// Implement if needed
		};

		transitionCompleted() {
			// Implement if needed
		};
	}

	return resetPwdViewModel;
});
