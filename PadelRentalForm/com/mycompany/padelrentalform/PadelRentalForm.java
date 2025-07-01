package com.mycompany.padelrentalform;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
 

public class PadelRentalForm extends JFrame {
   private JTextField tfNama, tfNoHP, tfTanggal, tfJamMulai, tfJamSelesai;
   private JComboBox<String> cbLapangan;
   
   public PadelRentalForm() {
       setTitle("Form Sewa Lapangan Padel");
       setSize(400, 350);
       setDefaultCloseOperation(EXIT_ON_CLOSE);
       setLayout(new GridLayout(7, 2, 10, 10));
       setLocationRelativeTo(null);

       tfNama = new JTextField();
       tfNoHP = new JTextField();
       tfTanggal = new JTextField("yyyy-mm-dd");
       tfJamMulai = new JTextField("08:00");
       tfJamSelesai = new JTextField("09:00");

       String[] lapanganOptions = { "Lapangan 1", "Lapangan 2", "Lapangan 3", "Lapangan 4" };
       cbLapangan = new JComboBox<>(lapanganOptions);

       JButton btnSubmit = new JButton("Simpan");

       add(new JLabel("Nama Penyewa:")); add(tfNama);
       add(new JLabel("No HP:")); add(tfNoHP);
       add(new JLabel("Tanggal Sewa:")); add(tfTanggal);
       add(new JLabel("Jam Mulai:")); add(tfJamMulai);
       add(new JLabel("Jam Selesai:")); add(tfJamSelesai);
       add(new JLabel("Lapangan:")); add(cbLapangan);
       add(btnSubmit); add(new JLabel());
       }


        JPanel bottomPanel = new JPanel();
        customerButton = new JButton("Kelola Customer");
        reservationButton = new JButton("Reservasi");
        logoutButton = new JButton("Logout");

        bottomPanel.add(customerButton);
        bottomPanel.add(reservationButton);

        customerButton.setVisible(false);
        reservationButton.setVisible(false);

        add(bottomPanel, BorderLayout.SOUTH);


        customerButton.addActionListener(e -> {
        CustomerForm customerForm = new CustomerForm(sharedCustomers, currentUser.getUsername());
        customerForm.setVisible(true);
        });

        reservationButton.addActionListener(e -> {
            ReservationForm reservationForm = new ReservationForm(sharedCustomers, currentUser.getUsername());
            reservationForm.setVisible(true);
        });

        Thread thread = new Thread(this);
        thread.start();

   }

   public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PadelRentalForm().setVisible(true));
   }
