package com.hcl.finalproject;

import java.io.Serializable;

public class User implements Serializable{

    private String id = "";
    private String name = "";
    private String username = "";
    private String email = "";
    public Address address = new Address();
    public Address getAddress(){
        return address;
    }
    public class Address implements Serializable {
        private String street = "";
        private String suite = "";
        private String city = "";
        private String zipcode = "";
        public Address() {
            super();
        }

        public Geo geo = new Geo();
        public Geo getGeo(){return geo;}
        public class Geo implements Serializable{
            private String lat = "";
            private String lng = "";

            public String getLat() {
                return lat;
            }

            public String getLng() {
                return lng;
            }

            public Geo() {
                super();
            }

            @Override
            public String toString() {
                return "Geo{" +
                        "lat='" + lat + '\'' +
                        ", lng='" + lng + '\'' +
                        '}';
            }
        }

        public String getStreet() {
            return street;
        }

        public String getSuite() {
            return suite;
        }

        public String getCity() {
            return city;
        }

        public String getZipcode() {
            return zipcode;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "street='" + street + '\'' +
                    ", suite='" + suite + '\'' +
                    ", city='" + city + '\'' +
                    ", zipcode='" + zipcode + '\'' +
                    ", geo=" + geo +
                    '}';
        }
    }
    private String phone = "";
    private String website = "";
    public Company company = new Company();
    public Company getCompany(){return company;}
    public class Company implements Serializable{
        private String name = "";
        private String catchPhrase = "";
        private String bs = "";

        public String getName() {
            return name;
        }

        public String getCatchPhrase() {
            return catchPhrase;
        }

        public String getBs() {
            return bs;
        }

        public Company() {
            super();
        }

        @Override
        public String toString() {
            return "Company{" +
                    "name='" + name + '\'' +
                    ", catchPhrase='" + catchPhrase + '\'' +
                    ", bs='" + bs + '\'' +
                    '}';
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                ", company=" + company +
                '}';
    }

    public User() {
        super();
    }

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
