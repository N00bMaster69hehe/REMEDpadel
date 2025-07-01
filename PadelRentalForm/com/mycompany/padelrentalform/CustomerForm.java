// Source code is decompiled from a .class file using FernFlower decompiler.
package com.mycompany.padelrentalform;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CustomerForm extends JFrame {
   private JTextField nameField;
   private JTextField phoneNumberField;
   private JTextField addressField;
   private JButton saveButton;
   private JButton deleteButton;
   private JTable customerTable;
   private DefaultTableModel tableModel;
   private ArrayList<String> registeredPhones = new ArrayList();
   private ArrayList<Customer> customers;
   private boolean isEditing = false;
   private int editingIndex = -1;
   private String currentUser;

   public CustomerForm(ArrayList<Customer> customers, String currentUser) {
      this.customers = customers;
      this.currentUser = currentUser;
      this.setTitle("WK. Cuan | Form Customer");
      this.setSize(700, 300);
      this.setDefaultCloseOperation(2);
      this.setLocationRelativeTo((Component)null);
      JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
      formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      formPanel.add(new JLabel("Nama:"));
      this.nameField = new JTextField();
      formPanel.add(this.nameField);
      formPanel.add(new JLabel("Nomor Telepon:"));
      this.phoneNumberField = new JTextField();
      formPanel.add(this.phoneNumberField);
      formPanel.add(new JLabel("Alamat:"));
      this.addressField = new JTextField();
      formPanel.add(this.addressField);
      JPanel buttonPanel = new JPanel(new FlowLayout(1, 15, 10));
      this.saveButton = new JButton("Simpan");
      this.deleteButton = new JButton("Hapus");
      buttonPanel.add(this.saveButton);
      buttonPanel.add(this.deleteButton);
      JPanel topPanel = new JPanel(new BorderLayout());
      topPanel.add(formPanel, "Center");
      topPanel.add(buttonPanel, "South");
      this.getContentPane().add(topPanel, "North");
      this.tableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Nomor Telepon", "Alamat", "Last Action By:"}, 0);
      this.customerTable = new JTable(this.tableModel);
      this.getContentPane().add(new JScrollPane(this.customerTable), "Center");
      this.saveButton.addActionListener((e) -> {
         this.saveCustomer();
      });
      this.deleteButton.addActionListener((e) -> {
         int selectedRow = this.customerTable.getSelectedRow();
         if (selectedRow != -1) {
            Customer removed = (Customer)customers.remove(selectedRow);
            this.registeredPhones.remove(removed.getPhoneNumber().toString());
            this.refreshTable();
            this.clearFields();
            this.isEditing = false;
            this.editingIndex = -1;
         } else {
            JOptionPane.showMessageDialog(this, "Pilih customer untuk dihapus.");
         }

      });
      this.customerTable.getSelectionModel().addListSelectionListener((e) -> {
         if (!e.getValueIsAdjusting()) {
            int selectedRow = this.customerTable.getSelectedRow();
            if (selectedRow != -1) {
               Customer c = (Customer)customers.get(selectedRow);
               this.nameField.setText(c.getName());
               this.phoneNumberField.setText(c.getPhoneNumber().toString());
               this.addressField.setText(c.getAddress());
               this.editingIndex = selectedRow;
               this.isEditing = true;
            } else {
               this.clearFields();
               this.isEditing = false;
               this.editingIndex = -1;
            }
         }

      });
      this.refreshTable();
   }

   private void saveCustomer() {
      try {
         String name = this.nameField.getText().trim();
         String phoneText = this.phoneNumberField.getText().trim();
         String address = this.addressField.getText().trim();
         if (name.isEmpty() || phoneText.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua kolom harus diisi!");
            return;
         }

         if (!phoneText.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Nomor telepon harus berupa angka!");
            return;
         }

         Long phoneNumber = Long.parseLong(phoneText);
         Customer newCustomer;
         if (this.isEditing && this.editingIndex != -1) {
            newCustomer = (Customer)this.customers.get(this.editingIndex);
            if (!newCustomer.getPhoneNumber().toString().equals(phoneText) && this.registeredPhones.contains(phoneText)) {
               JOptionPane.showMessageDialog(this, "Nomor telepon sudah terdaftar!");
               return;
            }

            this.registeredPhones.remove(newCustomer.getPhoneNumber().toString());
            this.registeredPhones.add(phoneText);
            newCustomer.setName(name);
            newCustomer.setPhoneNumber(phoneNumber);
            newCustomer.setAddress(address);
            newCustomer.getAuditInfo().setEditedBy(this.currentUser);
            newCustomer.getAuditInfo().setCreatedBy((String)null);
         } else {
            if (this.registeredPhones.contains(phoneText)) {
               JOptionPane.showMessageDialog(this, "Nomor telepon sudah terdaftar!");
               return;
            }

            newCustomer = new Customer(name, phoneNumber, address);
            newCustomer.getAuditInfo().setCreatedBy(this.currentUser);
            this.customers.add(newCustomer);
            this.registeredPhones.add(phoneText);
         }

         this.refreshTable();
         this.clearFields();
      } catch (Exception var6) {
         JOptionPane.showMessageDialog(this, "Input tidak valid!", "Error", 0);
      }

   }

   private void clearFields() {
      this.nameField.setText("");
      this.phoneNumberField.setText("");
      this.addressField.setText("");
   }

   private void refreshTable() {
      this.tableModel.setRowCount(0);

      Customer c;
      String lastActionBy;
      for(Iterator var2 = this.customers.iterator(); var2.hasNext(); this.tableModel.addRow(new Object[]{c.getId(), c.getName(), c.getPhoneNumber(), c.getAddress(), lastActionBy})) {
         c = (Customer)var2.next();
         lastActionBy = "-";
         AuditInfo audit = c.getAuditInfo();
         if (audit.getDeletedBy() != null) {
            lastActionBy = "Deleted by " + audit.getDeletedBy();
         } else if (audit.getEditedBy() != null) {
            lastActionBy = "Edited by " + audit.getEditedBy();
         } else if (audit.getCreatedBy() != null) {
            lastActionBy = "Created by " + audit.getCreatedBy();
         }
      }

   }
}
