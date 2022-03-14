/**
 * User: Vinicius Cardoso
 * Date: 02/04/14
 * Time: 08:23
 */

"use strict";

var Main = window.Main || {};

Main.View = function(){
    return {
        showModal: function(elem, show, size) {
            show = show == null ? true : show;
            size = size == null || size == undefined ? 'xxl' : size;
            if(elem !== null) {
                if (show) {
                    $(elem).find('.modal-dialog').attr('class','modal-dialog modal-' + size);
                    $(elem).modal('show');
                } else {
                    $(elem).modal('hide');
                }
            }
        },
        showToast: function(toastrType, texto, titulo) {
            if (titulo != undefined) {
                toastr[toastrType](texto, titulo);
            } else {
                toastr[toastrType](texto);
            }
        },
        showModalNotificacao: function(id, $grid) {
            Pace.track(function(){
                $.ajax({
                    type: 'post',
                    url: Main.Util.getBaseUrl("/painel/mensagem/view.partial"),
                    data: { id: id },
                    dataType: 'html',
                    success: function(data) {
                        $('#mvi-id').text(id);
                        $('#mvi-title').text("Visualizar Mensagem");
                        $('#mvi-container').html(data);
                        Main.View.showModal('#modal-view-iframe', true, 'lg');
                        Main.Sync.checkNotificacaoTotalUnread();
                        if($grid != undefined) $grid.ajax.reload(null, false);
                    },
                    error: function(data) {
                        Main.Util.alertDataError(this.url, data);
                    }
                });
            });
        },
        downloadFileAjax: function(url) {
            var $modalDownload = $("#modal-download");
            $modalDownload.modal('show');
            Pace.track(function(){
                $.fileDownload(url, {
                    successCallback: function (url) {
                        $modalDownload.modal('hide');
                    },
                    failCallback: function (responseHtml, url) {
                        $modalDownload.modal('hide');
                        Main.Util.alertError(url, responseHtml);
                    }
                });
            });
            return false;
        },
        showIframePrintModal: function(src, title, showPrintButton) {
            Pace.track(function(){
                var $modal = $('#modal-iframe');
                $modal.find('.modal-body').empty().append('<iframe id="iframe-print" name="iframe-print" width="100%" height="500px" src="" frameborder="0" align="center"></iframe>')

                var $iframe = $('#iframe-print');
                var $iframePrintBtn = $('#iframe-print-btn');
                $iframe.parents('.modal-content').find('.modal-title').text(title);
                $iframe.contents().find("body").html('');

                //Captura altura da janela
                var iframeHeight = $(window).height() - 60 - 61 - 30; //modal padding top+bottom; modal header; iframe padding top+bottom
                $iframe.prop('height', iframeHeight + 'px');

                if (showPrintButton) { $iframePrintBtn.show() } else { $iframePrintBtn.hide(); }
                $iframePrintBtn.off().on('click', function(e){
                    e.preventDefault();
                    window.frames['iframe-print'].focus();
                    window.frames['iframe-print'].print();
                });

                Main.View.showModal('#modal-iframe', true);
                $iframe.attr('src', src);
            });
        },
        dateTimeFormatter: function (cellvalue, options, rowObject) {
            return (!$.isEmptyObject(cellvalue)) ? moment(cellvalue.split(".")[0]).format("DD/MM/YYYY HH:mm") : "";
        },
        dateFormatter: function (cellvalue, options, rowObject) {
            return (!$.isEmptyObject(cellvalue)) ? moment(cellvalue).format("DD/MM/YYYY") : "";
        },
        dateWithProtocoloFormatter: function (cellvalue, options, rowObject) {
            var retorno = (!$.isEmptyObject(cellvalue)) ? moment(cellvalue).format("DD/MM/YYYY") : "";
            if(rowObject.hasProt == "yes") retorno += " (P)";
            return retorno;
        },
        setMomentJsLocalePt: function() {
            return moment.defineLocale('pt-br', {
                months : 'Janeiro_Fevereiro_Março_Abril_Maio_Junho_Julho_Agosto_Setembro_Outubro_Novembro_Dezembro'.split('_'),
                monthsShort : 'Jan_Fev_Mar_Abr_Mai_Jun_Jul_Ago_Set_Out_Nov_Dez'.split('_'),
                weekdays : 'Domingo_Segunda-feira_Terça-feira_Quarta-feira_Quinta-feira_Sexta-feira_Sábado'.split('_'),
                weekdaysShort : 'Dom_Seg_Ter_Qua_Qui_Sex_Sáb'.split('_'),
                weekdaysMin : 'Do_Se_Te_Qa_Qi_Se_Sa'.split('_'),
                longDateFormat : {
                    LT : 'HH:mm',
                    L : 'DD/MM/YYYY',
                    LL : 'D [de] MMMM [de] YYYY',
                    LLL : 'D [de] MMMM [de] YYYY [às] LT',
                    LLLL : 'dddd, D [de] MMMM [de] YYYY [às] LT'
                },
                calendar : {
                    sameDay: '[Hoje às] LT',
                    nextDay: '[Amanhã às] LT',
                    nextWeek: 'dddd [às] LT',
                    lastDay: '[Ontem às] LT',
                    lastWeek: function () {
                        return (this.day() === 0 || this.day() === 6) ?
                            '[Último] dddd [às] LT' : // Saturday + Sunday
                            '[Última] dddd [às] LT'; // Monday - Friday
                    },
                    sameElse: 'L'
                },
                relativeTime : {
                    future : 'em %s',
                    past : '%s atrás',
                    s : 'segundos',
                    m : 'um minuto',
                    mm : '%d minutos',
                    h : 'uma hora',
                    hh : '%d horas',
                    d : 'um dia',
                    dd : '%d dias',
                    M : 'um mês',
                    MM : '%d meses',
                    y : 'um ano',
                    yy : '%d anos'
                },
                ordinal : '%dº'
            });
        },
        multiSelectDefaultOptions: {
            keepOrder: true,
            selectableHeader: "<input type='text' class='search-input form-control' autocomplete='off' style='margin-bottom: 10px;' placeholder='Buscar...'>",
            selectionHeader: "<input type='text' class='search-input form-control' autocomplete='off' style='margin-bottom: 10px;' placeholder='Buscar...'>",
            afterInit: function(ms){
                var that = this;
                var $selectableSearch = that.$selectableUl.prev();
                var $selectionSearch = that.$selectionUl.prev();
                var selectableSearchString = '#'+that.$container.attr('id')+' .ms-elem-selectable:not(.ms-selected)';
                var selectionSearchString = '#'+that.$container.attr('id')+' .ms-elem-selection.ms-selected';
                that.qs1 = $selectableSearch.quicksearch(selectableSearchString)
                    .on('keydown', function(e){
                        if (e.which === 40){
                            that.$selectableUl.focus();
                            return false;
                        }
                    });
                that.qs2 = $selectionSearch.quicksearch(selectionSearchString)
                    .on('keydown', function(e){
                        if (e.which == 40){
                            that.$selectionUl.focus();
                            return false;
                        }
                    });
            },
            afterSelect: function(idxs){
                this.qs1.cache();
                this.qs2.cache();
            },
            afterDeselect: function(idxs){
                this.qs1.cache();
                this.qs2.cache();
            }
        },
        parseBrNumberToFloat: function(num) {
            return parseFloat(num.replace('.','').replace(',','.'));
        },
        numberFormatAndRound: function(num, places) {
            num = typeof(num) == "number" ? num : parseFloat(num);
            places = (typeof(places) == "number" && places > -1) ? places : 4;
            return num.toLocaleString('pt-BR', { maximumFractionDigits: places, minimumFractionDigits: places });
        },
        maskInputCelularNonoDigito: function(elem) {
            //elem.focusout(function(){
            //    var phone, element;
            //    element = $(this);
            //    element.unmask();
            //    phone = element.val().replace(/\D/g, '');
            //    if(phone.length > 10) {
            //        element.mask("(99) 99999-999?9");
            //    } else {
            //        element.mask("(99) 9999-9999?9");
            //    }
            //}).trigger('focusout');
            var maskBehavior = function (val) {
                    return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
                },
                options = {onKeyPress: function(val, e, field, options) {
                        field.mask(maskBehavior.apply({}, arguments), options);
                    }
                };

            elem.mask(maskBehavior, options);
        },
        applySelectPlugins: function () {
            $('.select2').select2({language: "pt-BR"});
            $(".multiSelect").multiSelect(Main.View.multiSelectDefaultOptions);
        },
        applyNumericPlugins: function () {
            $(".trf-integer").autoNumeric('init', {aSep: '.', aDec: ',', altDec: '.', mDec: '0'});
            $(".trf-double").autoNumeric('init', {aSep: '.', aDec: ',', altDec: '.'});
        },
        applyDateTimePlugins: function () {
            $(".trf-dt").datetimepicker({locale: 'pt-br', format: 'DD/MM/YYYY'});
            $(".trf-dt-hr").datetimepicker({locale: 'pt-br', format: 'DD/MM/YYYY HH:mm'});
        },
        dataTablesDateTimeFormatterWithSecs: function(data, type, row, meta) { return data != null && data != '' && data != '-' ? moment(data, "YYYY-MM-DD HH:mm:ss").format("DD/MM/YYYY HH:mm:ss") : ''; },
        dataTablesDateTimeFormatter: function(data, type, row, meta) { return data != null && data != '' && data != '-' ? moment(data, "YYYY-MM-DD HH:mm:ss").format("DD/MM/YYYY HH:mm") : ''; },
        dataTablesDateFormatter: function(data, type, row, meta) { return data != null && data != '' && data != '-' ? moment(data, "YYYY-MM-DD").format("DD/MM/YYYY") : ''; },
        dataTablesNumberFormatter: function(data, type, row, meta) { return data != null && data !== '' ? Main.View.numberFormatAndRound(data, 2) : ''; },
        dataTablesCurrencyFormatter: function(data, type, row, meta) { return data != null && data !== '' ? 'R$ ' + Main.View.numberFormatAndRound(data, 2) : ''; },
        generateColumnFilters: function($tbElem) {
            $tbElem.find('thead .filters th').each(function() {
                if ($(this).is('[data-number-only]')) {
                    $(this).html('<input type="text" class="form-control dt-filter" />');
                    $(this).find('input').autoNumeric('init', {aSep: '', mDec: '0'});
                } else if ($(this).is('[data-number-only-allow-negative]')) {
                    $(this).html('<input type="text" class="form-control dt-filter" />');
                    $(this).find('input').autoNumeric('init', {aSep: '.', aDec: ',', altDec: '.', mDec: '0', vMin: '-9999.99'});
                } else if ($(this).is('[data-ignore]')) {
                    //do nothing...
                } else {
                    $(this).html('<input type="text" class="form-control dt-filter" />');
                }
            });
        },
        addColumnFiltersListeners: function($grid) {
            $grid.columns().every(function() {
                var that = this;
                $('input', $(this.table().header()).find('.filters th:eq(' + this.index() + ')')).on('keyup change', function() {
                    if($(this).parent().is('[data-number-only-allow-negative]') && this.value === "-") {
                        return;
                    } else {
                        if (that.search() !== this.value) {
                            that.search(this.value).draw();
                        }
                    }
                });
            });
        },
        checkErrors: function(errors, errorMessage) {
            if(errors.length > 0) {
                var $modalError = $('#modal-error-blank');
                $modalError.find('.modal-title').text(errorMessage);
                var listaErrors = '<ul class="text-danger">';
                for(var i = 0; i < errors.length; i++) {
                    listaErrors += '<li>' + errors[i] + '</li>';
                }
                listaErrors += '</ul>';
                $modalError.find('.modal-body').empty();
                $modalError.find('.modal-body').append(listaErrors);
                Main.View.showModal('#modal-error-blank');
                return false;
            } else {
                return true;
            }
        }
    }
}();
