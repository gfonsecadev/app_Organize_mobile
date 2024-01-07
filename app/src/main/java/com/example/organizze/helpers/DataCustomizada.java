package com.example.organizze.helpers;

import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DataCustomizada {

    public static  String dataCustomizada(){
        long data=System.currentTimeMillis();

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        String dataformatada=simpleDateFormat.format(data);

        return dataformatada;
    }

    public static  String mesAtual(){
        long data = System.currentTimeMillis();

        SimpleDateFormat date = new SimpleDateFormat("MMMM yyyy");
        String dataMes = date.format(data);
        String[] array=dataMes.split(" ");
        String letra=array[0].substring(0,1);
        String upeer=letra.toUpperCase();
        String mesUpeer=array[0].replaceFirst(letra,upeer);
        dataMes=mesUpeer+" "+array[1];

    return dataMes;
    }

    public static void dataPosterior(TextView mes) {
        String[] arraydata = mes.getText().toString().split(" ");
        int ano = Integer.valueOf(arraydata[1]);
        String meses = arraydata[0];

        if (meses.equals("Janeiro") || meses.equals("janeiro")) {
            meses = "Fevereiro";
            mes.setText(meses + " " + ano);
        } else if (meses.equals("Fevereiro") || meses.equals("fevereiro")) {
            meses = "Março";
            mes.setText(meses + " " + ano);
        } else if (meses.equals("Março") || meses.equals("março")) {
            meses = "Abril";
            mes.setText(meses + " " + ano);
        } else if (meses.equals("abril") || meses.equals("Abril")) {
            meses = "Maio";
            mes.setText(meses + " " + ano);
        } else if (meses.equals("Maio") || meses.equals("maio")) {
            meses = "Junho";
            mes.setText(meses + " " + ano);
        } else if (meses.equals("Junho") || meses.equals("junho")) {
            meses = "Julho";
            mes.setText(meses + " " + ano);
        } else if (meses.equals("Julho") || meses.equals("julho")) {
            meses = "Agosto";
            mes.setText(meses + " " + ano);
        } else if (meses.equals("Agosto") || meses.equals("agosto")) {
            meses = "Setembro";
            mes.setText(meses + " " + ano);
        } else if (meses.equals("Setembro") || meses.equals("setembro")) {
            meses = "Outubro";
            mes.setText(meses + " " + ano);
        }else if (meses.equals("Outubro") || meses.equals("outubro")) {
            meses = "Novembro";
            mes.setText(meses + " " + ano);
        } else if (meses.equals("Novembro") || meses.equals("novembro")) {
            meses = "Dezembro";
            mes.setText(meses + " " + ano);
        }else if (meses.equals("Dezembro") || meses.equals("dezembro")) {
            meses = "Janeiro";
            ano=ano+1;
            mes.setText(meses + " " + ano);
        }



    }

    public static void dataAnterior(TextView mes) {
        String[] arraydata = mes.getText().toString().split(" ");
        int ano = Integer.valueOf(arraydata[1]);
        String meses = arraydata[0];
        if (meses.equals("Janeiro") || meses.equals("janeiro")) {
            meses = "Dezembro";
            ano=ano-1;
            mes.setText(meses + " " + ano);
        } else if (meses.equals("Fevereiro") || meses.equals("fevereiro")) {
            meses = "Janeiro";
            mes.setText(meses + " " + ano);
        } else if (meses.equals("Março") || meses.equals("março")) {
            meses = "Fevereiro";
            mes.setText(meses + " " + ano);
        } else if (meses.equals("abril") || meses.equals("Abril")) {
            meses = "Março";
            mes.setText(meses + " " + ano);
        } else if (meses.equals("Maio") || meses.equals("maio")) {
            meses = "Abril";
            mes.setText(meses + " " + ano);
        } else if (meses.equals("Junho") || meses.equals("junho")) {
            meses = "Maio";
            mes.setText(meses + " " + ano);
        } else if (meses.equals("Julho") || meses.equals("julho")) {
            meses = "Junho";
            mes.setText(meses + " " + ano);
        } else if (meses.equals("Agosto") || meses.equals("agosto")) {
            meses = "Julho";
            mes.setText(meses + " " + ano);
        } else if (meses.equals("Setembro") || meses.equals("setembro")) {
            meses = "Agosto";
            mes.setText(meses + " " + ano);
        }else if (meses.equals("Outubro") || meses.equals("outubro")) {
            meses = "Setembro";
            mes.setText(meses + " " + ano);
        } else if (meses.equals("Novembro") || meses.equals("novembro")) {
            meses = "Outubro";
            mes.setText(meses + " " + ano);
        }else if (meses.equals("Dezembro") || meses.equals("dezembro")) {
            meses = "Novembro";
            mes.setText(meses + " " + ano);
        }




    }
    public static String convertData(TextView mes){
        String[] arraydata = mes.getText().toString().split(" ");
        int ano = Integer.valueOf(arraydata[1]);
        String meses = arraydata[0];
        if (meses.equals("Janeiro") || meses.equals("janeiro")) {
            meses = "01";
        } else if (meses.equals("Fevereiro") || meses.equals("fevereiro")) {
            meses = "02";
        } else if (meses.equals("Março") || meses.equals("março")) {
            meses = "03";
        } else if (meses.equals("abril") || meses.equals("Abril")) {
            meses = "04";
        } else if (meses.equals("Maio") || meses.equals("maio")) {
            meses = "05";
        } else if (meses.equals("Junho") || meses.equals("junho")) {
            meses = "06";
        } else if (meses.equals("Julho") || meses.equals("julho")) {
            meses = "07";
        } else if (meses.equals("Agosto") || meses.equals("agosto")) {
            meses = "08";
        } else if (meses.equals("Setembro") || meses.equals("setembro")) {
            meses = "09";
        }else if (meses.equals("Outubro") || meses.equals("outubro")) {
            meses = "10";
        } else if (meses.equals("Novembro") || meses.equals("novembro")) {
            meses = "11";
        }else if (meses.equals("Dezembro") || meses.equals("dezembro")) {
            meses = "12";
        }
        return meses+"-"+ano;
    }
}
