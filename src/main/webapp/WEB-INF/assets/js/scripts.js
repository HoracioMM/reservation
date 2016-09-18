jQuery(document)
		.ready(
				function() {

					/*
					 * Resetting registration form text fields
					 */
					$(
							'.registration-form input[type="text"], .registration-form textarea')
							.on('focus', function() {
								$(this).val("");
								$(this).removeClass('input-error');

							});

					$('.registration-form')
							.on(
									'submit',
									function(e) {

										/** ***Empty fields validation**** */

										$(this)
												.find(
														'input[type="text"], textarea')
												.each(
														function() {
															if ($(this).val() == "") {
																e
																		.preventDefault();
																$(this)
																		.addClass(
																				'input-error');
															}

															else {
																$(this)
																		.removeClass(
																				'input-error');
															}
														});

										/** ***Passport Number validation**** */

										if (!/[a-zA-Z]{2}[0-9]{7}/.test($(this)
												.find('#form-passport').val())) {
										//	e.preventDefault();
											$(this)
													.find('#form-passport')
													.val(
															"Please enter valid passport number");
											$(this).find('#form-passport')
													.addClass('input-error');
										}

										/**
										 * ***Simple Mobile Number validation(10
										 * digit number with no leading
										 * zeroes)****
										 */

										if (!/^[1-9]{1}[0-9]{9}$/.test($(this)
												.find('#form-mobile').val())) {
										//	e.preventDefault();
											$(this)
													.find('#form-mobile')
													.val(
															"Please enter valid mobile number");
											$(this).find('#form-mobile')
													.addClass('input-error');
										}
										if (!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/
												.test($(this).find(
														'#form-email').val())) {
										//	e.preventDefault();
											$(this).find('#form-email').val(
													"Please enter valid email");
											$(this).find('#form-email')
													.addClass('input-error');
										}

									});

				});
