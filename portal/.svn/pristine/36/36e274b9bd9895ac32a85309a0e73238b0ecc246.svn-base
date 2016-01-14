! function(a) {
    "function" === typeof define && define.amd ? define(["jquery"], function(b) {
        a(b, window, document)
    }) : a(jQuery, window, document)
}(function(a, b, c, d) {
    "use strict";

    function j(b, c) {
        this.element = b, this.options = a.extend({}, g, c), this._defaults = g, this.ns = "." + e + f++, this.isGoodBrowser = Boolean(b.setSelectionRange), this.hadInitialPlaceholder = Boolean(a(b).attr("placeholder")), this._name = e
    }
    var e = "intlTelInput",
        f = 1,
        g = {
            allowExtensions: !1,
            autoFormat: !0,
            autoPlaceholder: !0,
            autoHideDialCode: !0,
            defaultCountry: "",
            ipinfoToken: "",
            nationalMode: !0,
            numberType: "MOBILE",
            onlyCountries: [],
            preferredCountries: ["us", "gb"],
            utilsScript: ""
        },
        h = {
            UP: 38,
            DOWN: 40,
            ENTER: 13,
            ESC: 27,
            PLUS: 43,
            A: 65,
            Z: 90,
            ZERO: 48,
            NINE: 57,
            SPACE: 32,
            BSPACE: 8,
            DEL: 46,
            CTRL: 17,
            CMD1: 91,
            CMD2: 224
        },
        i = !1;
    a(b).load(function() {
        i = !0
    }), j.prototype = {
        _init: function() {
            return this.options.nationalMode && (this.options.autoHideDialCode = !1), navigator.userAgent.match(/IEMobile/i) && (this.options.autoFormat = !1), this.isMobile = /Android.+Mobile|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent), this.autoCountryDeferred = new a.Deferred, this.utilsScriptDeferred = new a.Deferred, this._processCountryData(), this._generateMarkup(), this._setInitialState(), this._initListeners(), this._initRequests(), [this.autoCountryDeferred, this.utilsScriptDeferred]
        },
        _processCountryData: function() {
            this._setInstanceCountryData(), this._setPreferredCountries()
        },
        _addCountryCode: function(a, b, c) {
            b in this.countryCodes || (this.countryCodes[b] = []);
            var d = c || 0;
            this.countryCodes[b][d] = a
        },
        _setInstanceCountryData: function() {
            var b;
            if (this.options.onlyCountries.length) {
                for (b = 0; b < this.options.onlyCountries.length; b++) this.options.onlyCountries[b] = this.options.onlyCountries[b].toLowerCase();
                for (this.countries = [], b = 0; b < k.length; b++) - 1 != a.inArray(k[b].iso2, this.options.onlyCountries) && this.countries.push(k[b])
            } else this.countries = k;
            for (this.countryCodes = {}, b = 0; b < this.countries.length; b++) {
                var c = this.countries[b];
                if (this._addCountryCode(c.iso2, c.dialCode, c.priority), c.areaCodes)
                    for (var d = 0; d < c.areaCodes.length; d++) this._addCountryCode(c.iso2, c.dialCode + c.areaCodes[d])
            }
        },
        _setPreferredCountries: function() {
            this.preferredCountries = [];
            for (var a = 0; a < this.options.preferredCountries.length; a++) {
                var b = this.options.preferredCountries[a].toLowerCase(),
                    c = this._getCountryData(b, !1, !0);
                c && this.preferredCountries.push(c)
            }
        },
        _generateMarkup: function() {
            this.telInput = a(this.element), this.telInput.attr("autocomplete", "off"), this.telInput.wrap(a("<div>", {
                "class": "intl-tel-input"
            }));
            
            
            var b = a("<div>", {
                    "class": "flag-dropdown"
                }).insertAfter(this.telInput),
                c = a("<div>", {
                    "class": "selected-flag"
                }).appendTo(b);          
            this.selectedFlagInner = a("<div>", {
                "class": "iti-flag"
            }).appendTo(c), a("<div>", {
                "class": "arrow"
            }).appendTo(c),
            a("<div>", {
                "class": "myCountryISD"
            }).appendTo(c)
            , this.isMobile ? this.countryList = a("<select>").appendTo(b) : (this.countryList = a("<ul>", {
                "class": "country-list v-hide"
            }).appendTo(b), this.preferredCountries.length && !this.isMobile && (this._appendListItems(this.preferredCountries, "preferred"), a("<li>", {
                "class": "divider"
            }).appendTo(this.countryList))), this._appendListItems(this.countries, ""), this.isMobile || (this.dropdownHeight = this.countryList.outerHeight(), this.countryList.removeClass("v-hide").addClass("hide"), this.countryListItems = this.countryList.children(".country"))
        },
        _appendListItems: function(a, b) {
            for (var c = "", d = 0; d < a.length; d++) {
                var e = a[d];
                this.isMobile ? (c += "<option data-dial-code='" + e.dialCode + "' value='" + e.iso2 + "'>", c += e.name + " +" + e.dialCode, c += "</option>") : (c += "<li class='country " + b + "' data-dial-code='" + e.dialCode + "' data-country-code='" + e.iso2 + "'>", c += "<div class='flag'><div class='iti-flag " + e.iso2 + "'></div></div>", c += "<span class='country-name'>" + e.name + "</span>", c += "<span class='dial-code'>+" + e.dialCode + "</span>", c += "</li>")
            }
            this.countryList.append(c)
        },
        _setInitialState: function() {
            var a = this.telInput.val();
           
            this._getDialCode(a) ? this._updateFlagFromNumber(a, !0) : "auto" != this.options.defaultCountry && (this.options.defaultCountry = this.options.defaultCountry ? this._getCountryData(this.options.defaultCountry.toLowerCase(), !1, !1) : this.preferredCountries.length ? this.preferredCountries[0] : this.countries[0], this._selectFlag(this.options.defaultCountry.iso2), a || this._updateDialCode(this.options.defaultCountry.dialCode, !1)), a && this._updateVal(a);
            
            if($("#myCountryCode").val()){
            for (var b = 0; b < this.countries.length; b++){            	
            	if(this.countries[b].dialCode==$("#myCountryCode").val()){
            		this._selectFlag(this.countries[b].iso2);
            		break;
            	}
            }
            }else{
            	$("#myCountryCode").val(this.options.defaultCountry.dialCode);
            	  $(".myCountryISD").text(this.options.defaultCountry.dialCode);
            }           
                      
        },
        _initListeners: function() {
            var b = this;
            if (this._initKeyListeners(), (this.options.autoHideDialCode || this.options.autoFormat) && this._initFocusListeners(), this.isMobile) this.countryList.on("change" + this.ns, function() {
                b._selectListItem(a(this).find("option:selected"))
            });
            else {
                var c = this.telInput.closest("label");
                c.length && c.on("click" + this.ns, function(a) {
                    b.countryList.hasClass("hide") ? b.telInput.focus() : a.preventDefault()
                });
                var d = this.selectedFlagInner.parent();
                d.on("click" + this.ns, function() {
                    !b.countryList.hasClass("hide") || b.telInput.prop("disabled") || b.telInput.prop("readonly") || b._showDropdown()
                })
            }
        },
        _initRequests: function() {
            var c = this;
            this.options.utilsScript ? i ? this.loadUtils() : a(b).load(function() {
                c.loadUtils()
            }) : this.utilsScriptDeferred.resolve(), "auto" == this.options.defaultCountry ? this._loadAutoCountry() : this.autoCountryDeferred.resolve()
        },
        _loadAutoCountry: function() {
            var c = a.cookie ? a.cookie("itiAutoCountry") : "";
            if (c && (a.fn[e].autoCountry = c), a.fn[e].autoCountry) this.autoCountryLoaded();
            else if (!a.fn[e].startedLoadingAutoCountry) {
                a.fn[e].startedLoadingAutoCountry = !0;
                var d = "//ipinfo.io";
                this.options.ipinfoToken && (d += "?token=" + this.options.ipinfoToken), a.get(d, function() {}, "jsonp").always(function(b) {
                    a.fn[e].autoCountry = b && b.country ? b.country.toLowerCase() : "", a.cookie && a.cookie("itiAutoCountry", a.fn[e].autoCountry, {
                        path: "/"
                    }), a(".intl-tel-input input").intlTelInput("autoCountryLoaded")
                })
            }
        },
        _initKeyListeners: function() {
            var a = this;
            this.options.autoFormat && this.telInput.on("keypress" + this.ns, function(c) {
                if (c.which >= h.SPACE && !c.ctrlKey && !c.metaKey && b.intlTelInputUtils && !a.telInput.prop("readonly")) {
                    c.preventDefault();
                    var d = c.which >= h.ZERO && c.which <= h.NINE || c.which == h.PLUS,
                        e = a.telInput[0],
                        f = a.isGoodBrowser && e.selectionStart == e.selectionEnd,
                        g = a.telInput.attr("maxlength"),
                        i = a.telInput.val(),
                        j = g ? i.length < g : !0;
                    if (j && (d || f)) {
                        var k = d ? String.fromCharCode(c.which) : null;
                        a._handleInputKey(k, !0, d), i != a.telInput.val() && a.telInput.trigger("input")
                    }
                    d || a._handleInvalidKey()
                }
            }), this.telInput.on("cut" + this.ns + " paste" + this.ns, function() {
                setTimeout(function() {
                    if (a.options.autoFormat && b.intlTelInputUtils) {
                        var c = a.isGoodBrowser && a.telInput[0].selectionStart == a.telInput.val().length;
                        a._handleInputKey(null, c), a._ensurePlus()
                    } else a._updateFlagFromNumber(a.telInput.val())
                })
            }), this.telInput.on("keyup" + this.ns, function(c) {
                if (c.which == h.ENTER || a.telInput.prop("readonly"));
                else if (a.options.autoFormat && b.intlTelInputUtils) {
                    var d = a.isGoodBrowser && a.telInput[0].selectionStart == a.telInput.val().length;
                    a.telInput.val() ? (c.which == h.DEL && !d || c.which == h.BSPACE) && a._handleInputKey() : a._updateFlagFromNumber(""), a._ensurePlus()
                } else a._updateFlagFromNumber(a.telInput.val())
            })
        },
        _ensurePlus: function() {
            if (!this.options.nationalMode) {
                var a = this.telInput.val(),
                    b = this.telInput[0];
                if ("+" != a.charAt(0)) {
                    var c = this.isGoodBrowser ? b.selectionStart + 1 : 0;
                    this.telInput.val("+" + a), this.isGoodBrowser && b.setSelectionRange(c, c)
                }
            }
        },
        _handleInvalidKey: function() {
            var a = this;
            this.telInput.trigger("invalidkey").addClass("iti-invalid-key"), setTimeout(function() {
                a.telInput.removeClass("iti-invalid-key")
            }, 100)
        },
        _handleInputKey: function(a, b, c) {
            var f, d = this.telInput.val(),
                g = (this._getClean(d), this.telInput[0]),
                h = 0;
            if (this.isGoodBrowser ? (h = this._getDigitsOnRight(d, g.selectionEnd), a ? d = d.substr(0, g.selectionStart) + a + d.substring(g.selectionEnd, d.length) : f = d.substr(g.selectionStart - 2, 2)) : a && (d += a), this.setNumber(d, null, b, !0, c), this.isGoodBrowser) {
                var i;
                d = this.telInput.val(), h ? (i = this._getCursorFromDigitsOnRight(d, h), a || (i = this._getCursorFromLeftChar(d, i, f))) : i = d.length, g.setSelectionRange(i, i)
            }
        },
        _getCursorFromLeftChar: function(b, c, d) {
            for (var e = c; e > 0; e--) {
                var f = b.charAt(e - 1);
                if (a.isNumeric(f) || b.substr(e - 2, 2) == d) return e
            }
            return 0
        },
        _getCursorFromDigitsOnRight: function(b, c) {
            for (var d = b.length - 1; d >= 0; d--)
                if (a.isNumeric(b.charAt(d)) && 0 === --c) return d;
            return 0
        },
        _getDigitsOnRight: function(b, c) {
            for (var d = 0, e = c; e < b.length; e++) a.isNumeric(b.charAt(e)) && d++;
            return d
        },
        _initFocusListeners: function() {
            var a = this;
            this.options.autoHideDialCode && this.telInput.on("mousedown" + this.ns, function(b) {
                a.telInput.is(":focus") || a.telInput.val() || (b.preventDefault(), a.telInput.focus())
            }), this.telInput.on("focus" + this.ns, function() {
                var d = a.telInput.val();
                a.telInput.data("focusVal", d), a.options.autoHideDialCode && !d && !a.telInput.prop("readonly") && a.selectedCountryData.dialCode && (a._updateVal("+" + a.selectedCountryData.dialCode, null, !0), a.telInput.one("keypress.plus" + a.ns, function(c) {
                    if (c.which == h.PLUS) {
                        var d = a.options.autoFormat && b.intlTelInputUtils ? "+" : "";
                        a.telInput.val(d)
                    }
                }), setTimeout(function() {
                    var b = a.telInput[0];
                    if (a.isGoodBrowser) {
                        var c = a.telInput.val().length;
                        b.setSelectionRange(c, c)
                    }
                }))
            }), this.telInput.on("blur" + this.ns, function() {
                if (a.options.autoHideDialCode) {
                    var c = a.telInput.val(),
                        d = "+" == c.charAt(0);
                    if (d) {
                        var e = a._getNumeric(c);
                        e && a.selectedCountryData.dialCode != e || a.telInput.val("")
                    }
                    a.telInput.off("keypress.plus" + a.ns)
                }
                a.options.autoFormat && b.intlTelInputUtils && a.telInput.val() != a.telInput.data("focusVal") && a.telInput.trigger("change")
            })
        },
        _getNumeric: function(a) {
            return a.replace(/\D/g, "")
        },
        _getClean: function(a) {
            var b = "+" == a.charAt(0) ? "+" : "";
            return b + this._getNumeric(a)
        },
        _showDropdown: function() {
            this._setDropdownPosition();
            var a = this.countryList.children(".active");
            a.length && this._highlightListItem(a), this.countryList.removeClass("hide"), a.length && this._scrollTo(a), this._bindDropdownListeners(), this.selectedFlagInner.children(".arrow").addClass("up"), this.selectedFlagInner.children(".myCountryISD").addClass("up")
        },
        _setDropdownPosition: function() {
            var c = this.telInput.offset().top,
                d = a(b).scrollTop(),
                e = c + this.telInput.outerHeight() + this.dropdownHeight < d + a(b).height(),
                f = c - this.dropdownHeight > d,
                g = !e && f ? "-" + (this.dropdownHeight - 1) + "px" : "";
            this.countryList.css("top", g)
        },
        _bindDropdownListeners: function() {
            var b = this;
            this.countryList.on("mouseover" + this.ns, ".country", function() {
                b._highlightListItem(a(this))
            }), this.countryList.on("click" + this.ns, ".country", function() {
                b._selectListItem(a(this))
            });
            var d = !0;
            a("html").on("click" + this.ns, function() {
                d || b._closeDropdown(), d = !1
            });
            var e = "",
                f = null;
            a(c).on("keydown" + this.ns, function(a) {
                a.preventDefault(), a.which == h.UP || a.which == h.DOWN ? b._handleUpDownKey(a.which) : a.which == h.ENTER ? b._handleEnterKey() : a.which == h.ESC ? b._closeDropdown() : (a.which >= h.A && a.which <= h.Z || a.which == h.SPACE) && (f && clearTimeout(f), e += String.fromCharCode(a.which), b._searchForCountry(e), f = setTimeout(function() {
                    e = ""
                }, 1e3))
            })
        },
        _handleUpDownKey: function(a) {
            var b = this.countryList.children(".highlight").first(),
                c = a == h.UP ? b.prev() : b.next();
            c.length && (c.hasClass("divider") && (c = a == h.UP ? c.prev() : c.next()), this._highlightListItem(c), this._scrollTo(c))
        },
        _handleEnterKey: function() {
            var a = this.countryList.children(".highlight").first();
            a.length && this._selectListItem(a)
        },
        _searchForCountry: function(a) {
            for (var b = 0; b < this.countries.length; b++)
                if (this._startsWith(this.countries[b].name, a)) {
                    var c = this.countryList.children("[data-country-code=" + this.countries[b].iso2 + "]").not(".preferred");
                    this._highlightListItem(c), this._scrollTo(c, !0);
                    break
                }
        },
        _startsWith: function(a, b) {
            return a.substr(0, b.length).toUpperCase() == b
        },
        _updateVal: function(a, c, d, e, f) {
            var g;
            if (this.options.autoFormat && b.intlTelInputUtils && this.selectedCountryData) {
                g = "number" == typeof c && intlTelInputUtils.isValidNumber(a, this.selectedCountryData.iso2) ? intlTelInputUtils.formatNumberByType(a, this.selectedCountryData.iso2, c) : !e && this.options.nationalMode && "+" == a.charAt(0) && intlTelInputUtils.isValidNumber(a, this.selectedCountryData.iso2) ? intlTelInputUtils.formatNumberByType(a, this.selectedCountryData.iso2, intlTelInputUtils.numberFormat.NATIONAL) : intlTelInputUtils.formatNumber(a, this.selectedCountryData.iso2, d, this.options.allowExtensions, f);
                var h = this.telInput.attr("maxlength");
                h && g.length > h && (g = g.substr(0, h))
            } else g = a;
            this.telInput.val(g)
        },
        _updateFlagFromNumber: function(b, c) {
            b && this.options.nationalMode && this.selectedCountryData && "1" == this.selectedCountryData.dialCode && "+" != b.charAt(0) && ("1" != b.charAt(0) && (b = "1" + b), b = "+" + b);
            var d = this._getDialCode(b),
                e = null;
            if (d) {
                var f = this.countryCodes[this._getNumeric(d)],
                    g = this.selectedCountryData && -1 != a.inArray(this.selectedCountryData.iso2, f);
                if (!g || this._isUnknownNanp(b, d))
                    for (var h = 0; h < f.length; h++)
                        if (f[h]) {
                            e = f[h];
                            break
                        }
            } else "+" == b.charAt(0) && this._getNumeric(b).length ? e = "" : b && "+" != b || (e = this.options.defaultCountry.iso2);
            null !== e && this._selectFlag(e, c)
        },
        _isUnknownNanp: function(a, b) {
            return "+1" == b && this._getNumeric(a).length >= 4
        },
        _highlightListItem: function(a) {
            this.countryListItems.removeClass("highlight"), a.addClass("highlight")
        },
        _getCountryData: function(a, b, c) {
            for (var d = b ? k : this.countries, e = 0; e < d.length; e++)
                if (d[e].iso2 == a) return d[e];
            if (c) return null;
            throw new Error("No country data for '" + a + "'")
        },
        _selectFlag: function(a, b) {
            this.selectedCountryData = a ? this._getCountryData(a, !1, !1) : {}, b && this.selectedCountryData.iso2 && (this.options.defaultCountry = {
                iso2: this.selectedCountryData.iso2
            }), this.selectedFlagInner.attr("class", "iti-flag " + a);
            var c = a ? this.selectedCountryData.name + ": +" + this.selectedCountryData.dialCode : "Unknown";
            /*this.selectedFlagInner.parent().attr("title", c), this._updatePlaceholder(), this.isMobile ? this.countryList.val(a) : (this.countryListItems.removeClass("active"), a && this.countryListItems.find(".iti-flag." + a).first().closest(".country").addClass("active"))*/
            this.selectedFlagInner.parent().attr("title", c), this._updatePlaceholder(), this.isMobile ? this.countryList.val(a) : (this.countryListItems.removeClass("active"), a && this.countryListItems.find(".iti-flag." + a).first().closest(".country").addClass("active")) 		
        },
         _updatePlaceholder: function() {
          /*  if (b.intlTelInputUtils && !this.hadInitialPlaceholder && this.options.autoPlaceholder && this.selectedCountryData) {
                var a = this.selectedCountryData.iso2,
                    c = intlTelInputUtils.numberType[this.options.numberType || "FIXED_LINE"],
                    d = a ? intlTelInputUtils.getExampleNumber(a, this.options.nationalMode, c) : "";
                this.telInput.attr("placeholder", d)
            }*/
        },
        _selectListItem: function(a) {
            var b = this.isMobile ? "value" : "data-country-code";
            if (this._selectFlag(a.attr(b), !0), this.isMobile || this._closeDropdown(), this._updateDialCode(a.attr("data-dial-code"), !0), this.telInput.trigger("change"), this.telInput.focus(), this.isGoodBrowser) {
                var c = this.telInput.val().length;
                this.telInput[0].setSelectionRange(c, c);                
                $("#myCountryCode").val(a.attr("data-dial-code"));
               // $("#myCountryISD").val(a.attr("data-dial-code"));
               // $('.myCountryISD').attr('id', 'myCountryISD');
                  $(".myCountryISD").text(a.attr("data-dial-code"))
            }
        },
        _closeDropdown: function() {
            this.countryList.addClass("hide"), this.selectedFlagInner.children(".arrow").removeClass("up"), a(c).off(this.ns), a("html").off(this.ns), this.countryList.off(this.ns)
        },
        _scrollTo: function(a, b) {
            var c = this.countryList,
                d = c.height(),
                e = c.offset().top,
                f = e + d,
                g = a.outerHeight(),
                h = a.offset().top,
                i = h + g,
                j = h - e + c.scrollTop(),
                k = d / 2 - g / 2;
            if (h < e) b && (j -= k), c.scrollTop(j);
            else if (i > f) {
                b && (j += k);
                var l = d - g;
                c.scrollTop(j - l)
            }
        },
        _updateDialCode: function(b, c) {
            var e, d = this.telInput.val();
            if (b = "+" + b, this.options.nationalMode && "+" != d.charAt(0)) e = d;
            else if (d) {
                var f = this._getDialCode(d);
                if (f.length > 1) e = d.replace(f, b);
                else {
                    var g = "+" != d.charAt(0) ? a.trim(d) : "";
                    e = b + g
                }
            } else e = !this.options.autoHideDialCode || c ? b : "";
            this._updateVal(e, null, c)
        },
        _getDialCode: function(b) {
            var c = "";
            if ("+" == b.charAt(0))
                for (var d = "", e = 0; e < b.length; e++) {
                    var f = b.charAt(e);
                    if (a.isNumeric(f) && (d += f, this.countryCodes[d] && (c = b.substr(0, e + 1)), 4 == d.length)) break
                }
            return c
        },
        autoCountryLoaded: function() {
            "auto" == this.options.defaultCountry && (this.options.defaultCountry = a.fn[e].autoCountry, this._setInitialState(), this.autoCountryDeferred.resolve())
        },
        destroy: function() {
            this.isMobile || this._closeDropdown(), this.telInput.off(this.ns), this.isMobile ? this.countryList.off(this.ns) : (this.selectedFlagInner.parent().off(this.ns), this.telInput.closest("label").off(this.ns));
            var a = this.telInput.parent();
            a.before(this.telInput).remove()
        },
        getExtension: function() {
            return this.telInput.val().split(" ext. ")[1] || ""
        },
        getNumber: function(a) {
            return b.intlTelInputUtils ? intlTelInputUtils.formatNumberByType(this.telInput.val(), this.selectedCountryData.iso2, a) : ""
        },
        getNumberType: function() {
            return b.intlTelInputUtils ? intlTelInputUtils.getNumberType(this.telInput.val(), this.selectedCountryData.iso2) : -99
        },
        getSelectedCountryData: function() {
            return this.selectedCountryData || {}
        },
        getValidationError: function() {
            return b.intlTelInputUtils ? intlTelInputUtils.getValidationError(this.telInput.val(), this.selectedCountryData.iso2) : -99
        },
        isValidNumber: function() {
            var c = a.trim(this.telInput.val()),
                d = this.options.nationalMode ? this.selectedCountryData.iso2 : "";
            return b.intlTelInputUtils ? intlTelInputUtils.isValidNumber(c, d) : !1
        },
        loadUtils: function(b) {
            var c = this,
                d = b || this.options.utilsScript;
            !a.fn[e].loadedUtilsScript && d ? (a.fn[e].loadedUtilsScript = !0, a.ajax({
                url: d,
                success: function() {
                    a(".intl-tel-input input").intlTelInput("utilsLoaded")
                },
                complete: function() {
                    c.utilsScriptDeferred.resolve()
                },
                dataType: "script",
                cache: !0
            })) : this.utilsScriptDeferred.resolve()
        },
        selectCountry: function(a) {
            a = a.toLowerCase(), this.selectedFlagInner.hasClass(a) || (this._selectFlag(a, !0), this._updateDialCode(this.selectedCountryData.dialCode, !1))
        },
        setNumber: function(a, b, c, d, e) {
            this.options.nationalMode || "+" == a.charAt(0) || (a = "+" + a), this._updateFlagFromNumber(a), this._updateVal(a, b, c, d, e)
        },
        utilsLoaded: function() {
            this.options.autoFormat && this.telInput.val() && this._updateVal(this.telInput.val()), this._updatePlaceholder()
        }
    }, a.fn[e] = function(b) {
        var c = arguments;
        if (b === d || "object" === typeof b) {
            var f = [];
            return this.each(function() {
                if (!a.data(this, "plugin_" + e)) {
                    var c = new j(this, b),
                        d = c._init();
                    f.push(d[0]), f.push(d[1]), a.data(this, "plugin_" + e, c)
                }
            }), a.when.apply(null, f)
        }
        if ("string" === typeof b && "_" !== b[0]) {
            var g;
            return this.each(function() {
                var d = a.data(this, "plugin_" + e);
                d instanceof j && "function" === typeof d[b] && (g = d[b].apply(d, Array.prototype.slice.call(c, 1))), "destroy" === b && a.data(this, "plugin_" + e, null)
            }), g !== d ? g : this
        }
    }, a.fn[e].getCountryData = function() {
        return k
    };
    for (var k = [
            ["Afghanistan (\u202b\u0627\u0641\u063a\u0627\u0646\u0633\u062a\u0627\u0646\u202c\u200e)", "af", "93"],
            ["Albania (Shqip\xebri)", "al", "355"],
            ["Algeria (\u202b\u0627\u0644\u062c\u0632\u0627\u0626\u0631\u202c\u200e)", "dz", "213"],
            ["American Samoa", "as", "1684"],
            ["Andorra", "ad", "376"],
            ["Angola", "ao", "244"],
            ["Anguilla", "ai", "1264"],
            ["Antigua and Barbuda", "ag", "1268"],
            ["Argentina", "ar", "54"],
            ["Armenia (\u0540\u0561\u0575\u0561\u057d\u057f\u0561\u0576)", "am", "374"],
            ["Aruba", "aw", "297"],
            ["Australia", "au", "61"],
            ["Austria (\xd6sterreich)", "at", "43"],
            ["Azerbaijan (Az\u0259rbaycan)", "az", "994"],
            ["Bahamas", "bs", "1242"],
            ["Bahrain (\u202b\u0627\u0644\u0628\u062d\u0631\u064a\u0646\u202c\u200e)", "bh", "973"],
            ["Bangladesh (\u09ac\u09be\u0982\u09b2\u09be\u09a6\u09c7\u09b6)", "bd", "880"],
            ["Barbados", "bb", "1246"],
            ["Belarus (\u0411\u0435\u043b\u0430\u0440\u0443\u0441\u044c)", "by", "375"],
            ["Belgium (Belgi\xeb)", "be", "32"],
            ["Belize", "bz", "501"],
            ["Benin (B\xe9nin)", "bj", "229"],
            ["Bermuda", "bm", "1441"],
            ["Bhutan (\u0f60\u0f56\u0fb2\u0f74\u0f42)", "bt", "975"],
            ["Bolivia", "bo", "591"],
            ["Bosnia and Herzegovina (\u0411\u043e\u0441\u043d\u0430 \u0438 \u0425\u0435\u0440\u0446\u0435\u0433\u043e\u0432\u0438\u043d\u0430)", "ba", "387"],
            ["Botswana", "bw", "267"],
            ["Brazil (Brasil)", "br", "55"],
            ["British Indian Ocean Territory", "io", "246"],
            ["British Virgin Islands", "vg", "1284"],
            ["Brunei", "bn", "673"],
            ["Bulgaria (\u0411\u044a\u043b\u0433\u0430\u0440\u0438\u044f)", "bg", "359"],
            ["Burkina Faso", "bf", "226"],
            ["Burundi (Uburundi)", "bi", "257"],
            ["Cambodia (\u1780\u1798\u17d2\u1796\u17bb\u1787\u17b6)", "kh", "855"],
            ["Cameroon (Cameroun)", "cm", "237"],
            ["Canada", "ca", "1", 1, ["204", "226", "236", "249", "250", "289", "306", "343", "365", "387", "403", "416", "418", "431", "437", "438", "450", "506", "514", "519", "548", "579", "581", "587", "604", "613", "639", "647", "672", "705", "709", "742", "778", "780", "782", "807", "819", "825", "867", "873", "902", "905"]],
            ["Cape Verde (Kabu Verdi)", "cv", "238"],
            ["Caribbean Netherlands", "bq", "599", 1],
            ["Cayman Islands", "ky", "1345"],
            ["Central African Republic (R\xe9publique centrafricaine)", "cf", "236"],
            ["Chad (Tchad)", "td", "235"],
            ["Chile", "cl", "56"],
            ["China (\u4e2d\u56fd)", "cn", "86"],
            ["Colombia", "co", "57"],
            ["Comoros (\u202b\u062c\u0632\u0631 \u0627\u0644\u0642\u0645\u0631\u202c\u200e)", "km", "269"],
            ["Congo (DRC) (Jamhuri ya Kidemokrasia ya Kongo)", "cd", "243"],
            ["Congo (Republic) (Congo-Brazzaville)", "cg", "242"],
            ["Cook Islands", "ck", "682"],
            ["Costa Rica", "cr", "506"],
            ["C\xf4te d\u2019Ivoire", "ci", "225"],
            ["Croatia (Hrvatska)", "hr", "385"],
            ["Cuba", "cu", "53"],
            ["Cura\xe7ao", "cw", "599", 0],
            ["Cyprus (\u039a\u03cd\u03c0\u03c1\u03bf\u03c2)", "cy", "357"],
            ["Czech Republic (\u010cesk\xe1 republika)", "cz", "420"],
            ["Denmark (Danmark)", "dk", "45"],
            ["Djibouti", "dj", "253"],
            ["Dominica", "dm", "1767"],
            ["Dominican Republic (Rep\xfablica Dominicana)", "do", "1", 2, ["809", "829", "849"]],
            ["Ecuador", "ec", "593"],
            ["Egypt (\u202b\u0645\u0635\u0631\u202c\u200e)", "eg", "20"],
            ["El Salvador", "sv", "503"],
            ["Equatorial Guinea (Guinea Ecuatorial)", "gq", "240"],
            ["Eritrea", "er", "291"],
            ["Estonia (Eesti)", "ee", "372"],
            ["Ethiopia", "et", "251"],
            ["Falkland Islands (Islas Malvinas)", "fk", "500"],
            ["Faroe Islands (F\xf8royar)", "fo", "298"],
            ["Fiji", "fj", "679"],
            ["Finland (Suomi)", "fi", "358"],
            ["France", "fr", "33"],
            ["French Guiana (Guyane fran\xe7aise)", "gf", "594"],
            ["French Polynesia (Polyn\xe9sie fran\xe7aise)", "pf", "689"],
            ["Gabon", "ga", "241"],
            ["Gambia", "gm", "220"],
            ["Georgia (\u10e1\u10d0\u10e5\u10d0\u10e0\u10d7\u10d5\u10d4\u10da\u10dd)", "ge", "995"],
            ["Germany (Deutschland)", "de", "49"],
            ["Ghana (Gaana)", "gh", "233"],
            ["Gibraltar", "gi", "350"],
            ["Greece (\u0395\u03bb\u03bb\u03ac\u03b4\u03b1)", "gr", "30"],
            ["Greenland (Kalaallit Nunaat)", "gl", "299"],
            ["Grenada", "gd", "1473"],
            ["Guadeloupe", "gp", "590", 0],
            ["Guam", "gu", "1671"],
            ["Guatemala", "gt", "502"],
            ["Guinea (Guin\xe9e)", "gn", "224"],
            ["Guinea-Bissau (Guin\xe9 Bissau)", "gw", "245"],
            ["Guyana", "gy", "592"],
            ["Haiti", "ht", "509"],
            ["Honduras", "hn", "504"],
            ["Hong Kong (\u9999\u6e2f)", "hk", "852"],
            ["Hungary (Magyarorsz\xe1g)", "hu", "36"],
            ["Iceland (\xcdsland)", "is", "354"],
            ["India (\u092d\u093e\u0930\u0924)", "in", "91"],
            ["Indonesia", "id", "62"],
            ["Iran (\u202b\u0627\u06cc\u0631\u0627\u0646\u202c\u200e)", "ir", "98"],
            ["Iraq (\u202b\u0627\u0644\u0639\u0631\u0627\u0642\u202c\u200e)", "iq", "964"],
            ["Ireland", "ie", "353"],
            ["Israel (\u202b\u05d9\u05e9\u05e8\u05d0\u05dc\u202c\u200e)", "il", "972"],
            ["Italy (Italia)", "it", "39", 0],
            ["Jamaica", "jm", "1876"],
            ["Japan (\u65e5\u672c)", "jp", "81"],
            ["Jordan (\u202b\u0627\u0644\u0623\u0631\u062f\u0646\u202c\u200e)", "jo", "962"],
            ["Kazakhstan (\u041a\u0430\u0437\u0430\u0445\u0441\u0442\u0430\u043d)", "kz", "7", 1],
            ["Kenya", "ke", "254"],
            ["Kiribati", "ki", "686"],
            ["Kuwait (\u202b\u0627\u0644\u0643\u0648\u064a\u062a\u202c\u200e)", "kw", "965"],
            ["Kyrgyzstan (\u041a\u044b\u0440\u0433\u044b\u0437\u0441\u0442\u0430\u043d)", "kg", "996"],
            ["Laos (\u0ea5\u0eb2\u0ea7)", "la", "856"],
            ["Latvia (Latvija)", "lv", "371"],
            ["Lebanon (\u202b\u0644\u0628\u0646\u0627\u0646\u202c\u200e)", "lb", "961"],
            ["Lesotho", "ls", "266"],
            ["Liberia", "lr", "231"],
            ["Libya (\u202b\u0644\u064a\u0628\u064a\u0627\u202c\u200e)", "ly", "218"],
            ["Liechtenstein", "li", "423"],
            ["Lithuania (Lietuva)", "lt", "370"],
            ["Luxembourg", "lu", "352"],
            ["Macau (\u6fb3\u9580)", "mo", "853"],
            ["Macedonia (FYROM) (\u041c\u0430\u043a\u0435\u0434\u043e\u043d\u0438\u0458\u0430)", "mk", "389"],
            ["Madagascar (Madagasikara)", "mg", "261"],
            ["Malawi", "mw", "265"],
            ["Malaysia", "my", "60"],
            ["Maldives", "mv", "960"],
            ["Mali", "ml", "223"],
            ["Malta", "mt", "356"],
            ["Marshall Islands", "mh", "692"],
            ["Martinique", "mq", "596"],
            ["Mauritania (\u202b\u0645\u0648\u0631\u064a\u062a\u0627\u0646\u064a\u0627\u202c\u200e)", "mr", "222"],
            ["Mauritius (Moris)", "mu", "230"],
            ["Mexico (M\xe9xico)", "mx", "52"],
            ["Micronesia", "fm", "691"],
            ["Moldova (Republica Moldova)", "md", "373"],
            ["Monaco", "mc", "377"],
            ["Mongolia (\u041c\u043e\u043d\u0433\u043e\u043b)", "mn", "976"],
            ["Montenegro (Crna Gora)", "me", "382"],
            ["Montserrat", "ms", "1664"],
            ["Morocco (\u202b\u0627\u0644\u0645\u063a\u0631\u0628\u202c\u200e)", "ma", "212"],
            ["Mozambique (Mo\xe7ambique)", "mz", "258"],
            ["Myanmar (Burma) (\u1019\u103c\u1014\u103a\u1019\u102c)", "mm", "95"],
            ["Namibia (Namibi\xeb)", "na", "264"],
            ["Nauru", "nr", "674"],
            ["Nepal (\u0928\u0947\u092a\u093e\u0932)", "np", "977"],
            ["Netherlands (Nederland)", "nl", "31"],
            ["New Caledonia (Nouvelle-Cal\xe9donie)", "nc", "687"],
            ["New Zealand", "nz", "64"],
            ["Nicaragua", "ni", "505"],
            ["Niger (Nijar)", "ne", "227"],
            ["Nigeria", "ng", "234"],
            ["Niue", "nu", "683"],
            ["Norfolk Island", "nf", "672"],
            ["North Korea (\uc870\uc120 \ubbfc\uc8fc\uc8fc\uc758 \uc778\ubbfc \uacf5\ud654\uad6d)", "kp", "850"],
            ["Northern Mariana Islands", "mp", "1670"],
            ["Norway (Norge)", "no", "47"],
            ["Oman (\u202b\u0639\u064f\u0645\u0627\u0646\u202c\u200e)", "om", "968"],
            ["Pakistan (\u202b\u067e\u0627\u06a9\u0633\u062a\u0627\u0646\u202c\u200e)", "pk", "92"],
            ["Palau", "pw", "680"],
            ["Palestine (\u202b\u0641\u0644\u0633\u0637\u064a\u0646\u202c\u200e)", "ps", "970"],
            ["Panama (Panam\xe1)", "pa", "507"],
            ["Papua New Guinea", "pg", "675"],
            ["Paraguay", "py", "595"],
            ["Peru (Per\xfa)", "pe", "51"],
            ["Philippines", "ph", "63"],
            ["Poland (Polska)", "pl", "48"],
            ["Portugal", "pt", "351"],
            ["Puerto Rico", "pr", "1", 3, ["787", "939"]],
            ["Qatar (\u202b\u0642\u0637\u0631\u202c\u200e)", "qa", "974"],
            ["R\xe9union (La R\xe9union)", "re", "262"],
            ["Romania (Rom\xe2nia)", "ro", "40"],
            ["Russia (\u0420\u043e\u0441\u0441\u0438\u044f)", "ru", "7", 0],
            ["Rwanda", "rw", "250"],
            ["Saint Barth\xe9lemy (Saint-Barth\xe9lemy)", "bl", "590", 1],
            ["Saint Helena", "sh", "290"],
            ["Saint Kitts and Nevis", "kn", "1869"],
            ["Saint Lucia", "lc", "1758"],
            ["Saint Martin (Saint-Martin (partie fran\xe7aise))", "mf", "590", 2],
            ["Saint Pierre and Miquelon (Saint-Pierre-et-Miquelon)", "pm", "508"],
            ["Saint Vincent and the Grenadines", "vc", "1784"],
            ["Samoa", "ws", "685"],
            ["San Marino", "sm", "378"],
            ["S\xe3o Tom\xe9 and Pr\xedncipe (S\xe3o Tom\xe9 e Pr\xedncipe)", "st", "239"],
            ["Saudi Arabia (\u202b\u0627\u0644\u0645\u0645\u0644\u0643\u0629 \u0627\u0644\u0639\u0631\u0628\u064a\u0629 \u0627\u0644\u0633\u0639\u0648\u062f\u064a\u0629\u202c\u200e)", "sa", "966"],
            ["Senegal (S\xe9n\xe9gal)", "sn", "221"],
            ["Serbia (\u0421\u0440\u0431\u0438\u0458\u0430)", "rs", "381"],
            ["Seychelles", "sc", "248"],
            ["Sierra Leone", "sl", "232"],
            ["Singapore", "sg", "65"],
            ["Sint Maarten", "sx", "1721"],
            ["Slovakia (Slovensko)", "sk", "421"],
            ["Slovenia (Slovenija)", "si", "386"],
            ["Solomon Islands", "sb", "677"],
            ["Somalia (Soomaaliya)", "so", "252"],
            ["South Africa", "za", "27"],
            ["South Korea (\ub300\ud55c\ubbfc\uad6d)", "kr", "82"],
            ["South Sudan (\u202b\u062c\u0646\u0648\u0628 \u0627\u0644\u0633\u0648\u062f\u0627\u0646\u202c\u200e)", "ss", "211"],
            ["Spain (Espa\xf1a)", "es", "34"],
            ["Sri Lanka (\u0dc1\u0dca\u200d\u0dbb\u0dd3 \u0dbd\u0d82\u0d9a\u0dcf\u0dc0)", "lk", "94"],
            ["Sudan (\u202b\u0627\u0644\u0633\u0648\u062f\u0627\u0646\u202c\u200e)", "sd", "249"],
            ["Suriname", "sr", "597"],
            ["Swaziland", "sz", "268"],
            ["Sweden (Sverige)", "se", "46"],
            ["Switzerland (Schweiz)", "ch", "41"],
            ["Syria (\u202b\u0633\u0648\u0631\u064a\u0627\u202c\u200e)", "sy", "963"],
            ["Taiwan (\u53f0\u7063)", "tw", "886"],
            ["Tajikistan", "tj", "992"],
            ["Tanzania", "tz", "255"],
            ["Thailand (\u0e44\u0e17\u0e22)", "th", "66"],
            ["Timor-Leste", "tl", "670"],
            ["Togo", "tg", "228"],
            ["Tokelau", "tk", "690"],
            ["Tonga", "to", "676"],
            ["Trinidad and Tobago", "tt", "1868"],
            ["Tunisia (\u202b\u062a\u0648\u0646\u0633\u202c\u200e)", "tn", "216"],
            ["Turkey (T\xfcrkiye)", "tr", "90"],
            ["Turkmenistan", "tm", "993"],
            ["Turks and Caicos Islands", "tc", "1649"],
            ["Tuvalu", "tv", "688"],
            ["U.S. Virgin Islands", "vi", "1340"],
            ["Uganda", "ug", "256"],
            ["Ukraine (\u0423\u043a\u0440\u0430\u0457\u043d\u0430)", "ua", "380"],
            ["United Arab Emirates (\u202b\u0627\u0644\u0625\u0645\u0627\u0631\u0627\u062a \u0627\u0644\u0639\u0631\u0628\u064a\u0629 \u0627\u0644\u0645\u062a\u062d\u062f\u0629\u202c\u200e)", "ae", "971"],
            ["United Kingdom", "gb", "44"],
            ["United States", "us", "1", 0],
            ["Uruguay", "uy", "598"],
            ["Uzbekistan (O\u02bbzbekiston)", "uz", "998"],
            ["Vanuatu", "vu", "678"],
            ["Vatican City (Citt\xe0 del Vaticano)", "va", "39", 1],
            ["Venezuela", "ve", "58"],
            ["Vietnam (Vi\u1ec7t Nam)", "vn", "84"],
            ["Wallis and Futuna", "wf", "681"],
            ["Yemen (\u202b\u0627\u0644\u064a\u0645\u0646\u202c\u200e)", "ye", "967"],
            ["Zambia", "zm", "260"],
            ["Zimbabwe", "zw", "263"]
        ], l = 0; l < k.length; l++) {
        var m = k[l];
        k[l] = {
            name: m[0],
            iso2: m[1],
            dialCode: m[2],
            priority: m[3] || 0,
            areaCodes: m[4] || null
        }
    }
});