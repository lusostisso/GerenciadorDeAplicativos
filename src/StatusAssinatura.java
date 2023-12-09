public enum StatusAssinatura {
    ATIVO("ativo"),
    INATIVO("inativo");

    private final String status;

    StatusAssinatura(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public StatusAssinatura inverterStatus() {
        return this == ATIVO ? INATIVO : ATIVO;
    }

    public static StatusAssinatura fromString(String status) {
        for (StatusAssinatura s : StatusAssinatura.values()) {
            if (s.status.equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Status de assinatura inválido: " + status);
    }
}
