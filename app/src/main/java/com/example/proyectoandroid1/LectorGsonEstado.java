package com.example.proyectoandroid1;

import java.util.List;

public class LectorGsonEstado {

    /**
     * estados : [{"id":"1","estado":"NUEVA"},{"id":"2","estado":"DUPLICADA"},{"id":"3","estado":"EN PROCESO"},{"id":"4","estado":"CERRADA"}]
     * success : 1
     */

    private int success;
    private List<EstadosBean> estados;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<EstadosBean> getEstados() {
        return estados;
    }

    public void setEstados(List<EstadosBean> estados) {
        this.estados = estados;
    }

    public static class EstadosBean {
        /**
         * id : 1
         * estado : NUEVA
         */

        private String id;
        private String estado;

        public EstadosBean(String id, String estado) {
            this.id = id;
            this.estado = estado;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }
    }
}
