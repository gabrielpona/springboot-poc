/**
 * User: Vinicius Cardoso
 * Date: 02/04/14
 * Time: 08:23
 */

"use strict";

var Main = window.Main || {};

Main.Util = function(){
    return {
        getBaseUrl: function (complemento) {
            var url = "";
            if (document.location.port === "80" || document.location.port === "") {
                url = document.location.protocol + '//' + document.location.hostname + document.location.pathname;
            } else {
                url = document.location.protocol + '//' + document.location.hostname + ':' + document.location.port + document.location.pathname;
            }

            var url2 = url.split('/');
            var url3 = "";
            if (this.containsString(url, 'localhost')) {
                for (var i = 0; i < 3; i++) {
                    url3 += url2[i] + '/';
                }
                if(this.containsString(url3,'https')) {
                    url3 = url3.replace(/\/\//g, '/').replace(/https:\//g, 'https://');
                } else {
                    url3 = url3.replace(/\/\//g, '/').replace(/http:\//g, 'http://');
                }

            } else {
                for (var i = 0; i < 3; i++) {
                    url3 += url2[i] + '/';
                }
            }
            url3 = url3.substring(0, url3.length - 1);
            return complemento !== undefined && complemento !== null ? url3 + complemento : url3;
        },
        containsString: function (strBase, strProcura) {
            if (strBase.indexOf(strProcura) === -1) {
                return false;
            } else {
                return true;
            }
        },
        isNullMapa: function(mapa) {
            for (var i = 0; i < mapa.length; i++) {
                if (mapa[i][1] > 0) return false;
            }
            return true;
        },
        isNullList: function(list) {
            for (var i = 0; i < list.length; i++) {
                if (list[i] > 0) return false;
            }
            return true;
        },
        checkPassword: function (senha1, senha2) {
            return (senha1 === senha2) && (senha1 !== "" && senha2 !== "");
        },
        countDaysUntilToday: function(data) {
            if($.isEmptyObject(data)) {
                console.error("Data para comparar vazia!");
                return;
            }
            var dataHoje = moment();
            var dataComparar = moment(data);
            var ts = dataComparar.diff(dataHoje, 'days');
            return ts;
        },
        formatDecimal: function (num) {
            var numSpl = new Number(num).toString().split('.');
            //Checagem decimais
            if (numSpl.length > 1 && numSpl[1].length === 1) {
                numSpl[1] += "0";
            }

            //Checagem inteiros
            var inteiros = numSpl[0].split('');
            var iteracoes = inteiros.length / 3;
            var indice = null;
            for (var i = iteracoes; i > 0; i--) {
                if (i !== iteracoes) {
                    indice = (3 * i) - inteiros.length;
                    inteiros.splice(indice, 0, ".");
                }
            }
            numSpl[0] = inteiros.join('');
            return numSpl.join(',');
        },
        decimalRound: function (num, places) {
            var dec = parseFloat(num.replace(/,/g, '.'));
            return dec.toFixed(places).replace(/\./g, ',');
        },
        leftPad: function (num, length) {
            var str = "" + num;
            var pad = "";
            for(var i = 0; i < length; i++) { pad += "0"; }
            return pad.substring(0, pad.length - str.length) + str;
        },
        generateRandomString: function(length, includeLower, includeNumber, includeSpecial) {
            var source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            if (includeLower) source += "abcdefghijklmnopqrstuvwxyz";
            if (includeNumber) source += "01234567890";
            if (includeSpecial) source += "!@#$%&*()[]{}çÇ<>";

            var randomLength = source.length;
            var randomString = "";
            for (var i = 0; i < length; i++) {
                randomString += source.charAt(Math.floor(Math.random() * randomLength))
            }
            return randomString;
        },
        alertDataError: function(url, data) {
            if (data != null && data != undefined && Main.Util.containsString(data.responseText, ":::")) {
                var title = data.responseText.split(":::")[0];
                var texto = data.responseText.split(":::")[1];

                $("#error-message").text(title);
                if (Main.Util.containsString(texto, "AppException")) {
                    $("#error-stacktrace").text(texto.split('at ')[0]);
                } else {
                    $("#error-stacktrace").text(texto);
                }
                $("#modal-error").modal('show');
            } else {
                $("#error-message").text("Ocorreu um erro.");
                if (Main.Util.containsString(data.responseText, "AppException")) {
                    $("#error-stacktrace").text(data.responseText.split('at ')[0]);
                } else {
                    $("#error-stacktrace").text(data.responseText);
                }
                $("#modal-error").modal('show');
            }
        },
        alertError: function(url, msg) {
            if (msg != null && msg != undefined && Main.Util.containsString(msg, ":::")) {
                var title = msg.split(":::")[0];
                var texto = msg.split(":::")[1];

                $("#error-message").text(title);
                if (Main.Util.containsString(texto, "AppException")) {
                    $("#error-stacktrace").text(texto.split('at ')[0]);
                } else {
                    $("#error-stacktrace").text(texto);
                }
                $("#modal-error").modal('show');
            } else {
                $("#error-message").text("Ocorreu um erro.");
                if (Main.Util.containsString(msg, "AppException")) {
                    $("#error-stacktrace").text(msg.split('at ')[0]);
                } else {
                    $("#error-stacktrace").text(msg);
                }
                $("#modal-error").modal('show');
            }
        },
        dateBrToUs: function(dateBr,meiaNoite){
            var dia = dateBr.substr(0,2);
            var mes = dateBr.substr(3,2);
            var ano = dateBr.substr(6,4);
            var dateUs;

            if(meiaNoite){
                dateUs = new Date(ano+"-"+mes+"-"+dia+ " 00:00:00");
            }else{
                dateUs = new Date(ano+"-"+mes+"-"+dia+ " 23:59:59");
            }


            return dateUs;
        }
    }
}();
