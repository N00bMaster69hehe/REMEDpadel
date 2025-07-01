// Source code is decompiled from a .class file using FernFlower decompiler.
package com.mycompany.padelrentalform;

public class Customer {
   private static int counter = 1;
   private String id;
   private String name;
   private Long phoneNumber;
   private String address;
   private AuditInfo auditInfo = new AuditInfo();

   public Customer(String name, Long phoneNumber, String address) {
      this.id = String.format("C%03d", counter++);
      this.name = name;
      this.phoneNumber = phoneNumber;
      this.address = address;
   }

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Long getPhoneNumber() {
      return this.phoneNumber;
   }

   public void setPhoneNumber(Long phoneNumber) {
      this.phoneNumber = phoneNumber;
   }

   public String getAddress() {
      return this.address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public AuditInfo getAuditInfo() {
      return this.auditInfo;
   }

   public void setAuditInfo(AuditInfo auditInfo) {
      this.auditInfo = auditInfo;
   }
}
