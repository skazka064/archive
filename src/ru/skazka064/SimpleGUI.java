package ru.skazka064;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SimpleGUI extends JFrame {
    private JLabel label = new JLabel(" Программа для проверки архива ");
    private JLabel label3 = new JLabel(" Введите путь до проверяемых папок: ");
    private JButton button = new JButton("Проверить");
    private JLabel label2 = new JLabel(" ® Березин ");
    private JScrollPane jScrollPane = new JScrollPane(null);
    private JTextField input = new JTextField("", 4);



    public SimpleGUI(){
        super("Archive");
        this.setBounds(500, 100, 450, 210);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(5,3,1,1));
        Font font = new Font("Verdana",Font.BOLD, 14);
        Font fontBig = new Font("Verdana",Font.BOLD, 16);




        label.setFont(fontBig);
        label3.setFont(font);
        button.setFont(font);
        input.setFont(font);
        label2.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label3.setHorizontalAlignment(SwingConstants.CENTER);




        button.addActionListener(new ButtonEventListener());
        container.add(label);
        container.add(label3);
        container.add(input);
        container.add(button);
        container.add(label2);
    }
    class ButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e){

            class PdfFiltr implements FilenameFilter {

                @Override
                public boolean accept(File dir, String name) {
                    return name.matches(".*pdf$");
                }
            }

            class SigFiltr implements FilenameFilter{

                @Override
                public boolean accept(File dir, String name) {
                    return name.matches(".*sig$");
                }
            }




                    String dir = input.getText();

                    File f0 = new File(dir);
                    ArrayList<String> cad = new ArrayList<>(Arrays.asList(f0.list()));
                    ArrayList<String> tom = null;
                    ArrayList<String> rez = null;
                    ArrayList<String> err = new ArrayList<>();
                    ArrayList<String> errDir = new ArrayList<>();
                    ArrayList<String> errTom = new ArrayList<>();
                    FilenameFilter sig = new SigFiltr();
                    FilenameFilter pdf = new PdfFiltr();



                    Matcher matcher = null;
                    for (int i =0;i < cad.size(); i++){
                        Pattern pattern= Pattern.compile("\\d\\d_\\d\\d_\\d\\d\\d\\d\\d\\d_\\d+");
                         matcher = pattern.matcher(cad.get(i));
                         if (matcher.find()){

                         }else{
                             errDir.add("\\"+cad.get(i)+"\\");
                         }

                    }


                    for (int j = 0; j < cad.size(); j++) {


                        File fileCad = new File(dir + "\\" + cad.get(j));

                        tom = new ArrayList<>(Arrays.asList(fileCad.list()));




                        for (int k = 0; k < tom.size(); k++) {


                            File fileTom = new File(dir + "\\" + cad.get(j) + "\\" + tom.get(k));

                            Pattern pattern = Pattern.compile("^Том_\\d+скан$");
                            matcher = pattern.matcher(tom.get(k));

                            if (matcher.find()) {

                            } else {
                                errTom.add("\\" + (cad.get(j) + "\\" + tom.get(k) + "\\"));
                            }



                            ArrayList<String> listSig = new ArrayList<String>(Arrays.asList(fileTom.list(sig)));
                            ArrayList<String> listPdf = new ArrayList<String>(Arrays.asList(fileTom.list(pdf)));
                            ArrayList<String> listNewSig = new ArrayList<String>();

                            for (int i = 0; i < listSig.size(); i++) {

                                int lenght = listSig.get(i).length();
                                listNewSig.add(listSig.get(i).substring(0, lenght - 4));


                            }


                            if (listNewSig.equals(listPdf)) {

                            } else {

                                err.add(fileTom.getAbsoluteFile() + " - отсутствует sig файл! " + "\n");
                            }


                        }
                    }






                    SimpleGUI simpleGUI = new SimpleGUI();


                    simpleGUI.setVisible(true);


                    String s1 = "";
                    if (err.isEmpty()){
                        s1="Каждому PDF соответствует свой SIG!"+"\n";
                    }else {
                        for (int i=0; i<err.size();i++){
                            s1+= err.get(i)+"\n";
                        }
                    }
                    String s2 ="";
                    if (errDir.isEmpty()){
                        s2= "Все директории 64_xx_xx_xxxxxx_x написаны правильно!"+"\n";
                    }else {
                        for (int i= 0 ; i<errDir.size();i++){
                            s2+= errDir.get(i)+ " - ошибка в названии директории 64_xx_xx_xxxxxx_x! " +"\n";
                        }
                    }
                    String s3 ="";
                    if (errTom.isEmpty()){
                        s3="Все директории Том написаны правильно!"+"\n";
                    }else {
                        for (int i=0; i<errTom.size(); i++){
                            s3+= errTom.get(i)+" - ошибка в названии директории Том_[число]скан!"+"\n";
                        }
                    }


             String s = s1+s2+s3;
            JTextArea textArea = new JTextArea(s);
            Font font = new Font("Verdana",Font.BOLD, 12);
            textArea.setFont(font);
            JScrollPane scrollPane = new JScrollPane(textArea);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            scrollPane.setPreferredSize( new Dimension( 650, 500 ) );
            JOptionPane.showMessageDialog(null, scrollPane, "Output", JOptionPane.YES_NO_OPTION);
            }




        }
    }