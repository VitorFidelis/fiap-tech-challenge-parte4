package br.feedback.service;

public enum Urgencia {
    BAIXA,
    MEDIA,
    ALTA;
    
    public static Urgencia calcular(double nota) {
        if (nota >= 8.0) return Urgencia.BAIXA;
        if (nota >= 5.0) return Urgencia.MEDIA;
        return Urgencia.ALTA;
    }
}
