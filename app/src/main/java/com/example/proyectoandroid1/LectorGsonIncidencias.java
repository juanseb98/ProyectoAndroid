package com.example.proyectoandroid1;

import java.util.List;

public class LectorGsonIncidencias {

    /**
     * incidencias : [{"id":"1","descripcion":"SE HA FUNDIDO UNA BOMBILLA","fecha":"2018-12-23 20:33:11","avisonombre":"FERNANDO","avisotfno":"616616616","estado":"NUEVA"},{"id":"2","descripcion":"ROTURA BOMBIN","fecha":"2018-12-23 00:00:00","avisonombre":"fernando","avisotfno":"233233233","estado":"NUEVA"},{"id":"3","descripcion":"Vayase señor Cuesta, vayase.","fecha":"2019-01-07 19:05:49","avisonombre":"Diego","avisotfno":"615271015","estado":"NUEVA"},{"id":"4","descripcion":"I am the best","fecha":"2019-01-07 19:11:52","avisonombre":"DIEGO","avisotfno":"615271015","estado":"NUEVA"},{"id":"5","descripcion":"La calefaccion no va :(","fecha":"2019-01-12 18:04:19","avisonombre":"Segismundo","avisotfno":"622211047","estado":"NUEVA"},{"id":"6","descripcion":"La lanzadera para la salida con el traje de Iron Man no fufa.","fecha":"2019-01-12 18:30:17","avisonombre":"Tony Stark","avisotfno":"685423158","estado":"NUEVA"},{"id":"7","descripcion":"Rotura 2","fecha":"2019-01-13 08:58:16","avisonombre":"Conserje","avisotfno":"","estado":"NUEVA"},{"id":"14","descripcion":"hola","fecha":"2019-01-16 17:30:51","avisonombre":"prueba","avisotfno":"645454545","estado":"NUEVA"},{"id":"15","descripcion":"test","fecha":"2019-01-17 14:29:16","avisonombre":"testeador","avisotfno":"666","estado":"NUEVA"}]
     * success : 1
     */

    private int success;
    private List<IncidenciasBean> incidencias;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<IncidenciasBean> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(List<IncidenciasBean> incidencias) {
        this.incidencias = incidencias;
    }

    public static class IncidenciasBean {
        /**
         * id : 1
         * descripcion : SE HA FUNDIDO UNA BOMBILLA
         * fecha : 2018-12-23 20:33:11
         * avisonombre : FERNANDO
         * avisotfno : 616616616
         * estado : NUEVA
         */

        private String id;
        private String descripcion;
        private String fecha;
        private String avisonombre;
        private String avisotfno;
        private String estado;

        public IncidenciasBean(String id, String descripcion, String fecha, String avisonombre, String avisotfno, String estado) {
            this.id = id;
            this.descripcion = descripcion;
            this.fecha = fecha;
            this.avisonombre = avisonombre;
            this.avisotfno = avisotfno;
            this.estado = estado;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }

        public String getAvisonombre() {
            return avisonombre;
        }

        public void setAvisonombre(String avisonombre) {
            this.avisonombre = avisonombre;
        }

        public String getAvisotfno() {
            return avisotfno;
        }

        public void setAvisotfno(String avisotfno) {
            this.avisotfno = avisotfno;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }
    }
}
