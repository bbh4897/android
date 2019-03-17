package com.example.burcu.bpfirstlevel_v1;

    public class Model3 {

        private int id;
        private String wifis;
        private String hedefKonum;


        public Model3(int id, String wifis, String hedefKonum){
            this.id = id;
            this.wifis = wifis;
            this.hedefKonum = hedefKonum;

        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getWifis() {
            return wifis;
        }

        public void setWifis(String wifis) {
            this.wifis = wifis;
        }

        public String getHedefKonum() {
            return hedefKonum;
        }

        public void setHedefKonum(String hedefKonum) {
            this.hedefKonum = hedefKonum;
        }
    }
