package com.hcl.finalproject;

import java.io.Serializable;

public class User implements Serializable{

    private String id = "";
    private String name = "";
    private String username = "";
    private String email = "";
    private String imageUri = "";
    private Boolean imageCached = false;
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

            public void setLat(String lat) {
                this.lat = lat;
            }

            public void setLng(String lng) {
                this.lng = lng;
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

        public void setStreet(String street) {
            this.street = street;
        }

        public void setSuite(String suite) {
            this.suite = suite;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
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

        public void setName(String name) {
            this.name = name;
        }

        public void setCatchPhrase(String catchPhrase) {
            this.catchPhrase = catchPhrase;
        }

        public void setBs(String bs) {
            this.bs = bs;
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

    public String getImageUri() {
        return imageUri;
    }

    public Boolean getImageCached() {
        return imageCached;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public void setImageCached(Boolean imageCached) {
        this.imageCached = imageCached;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", imageUri='" + imageUri + '\'' +
                ", imageCached=" + imageCached +
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

    public User(String id, String name, String username, String email, Address address, String phone, String website, Company company) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.company = company;
    }
}
