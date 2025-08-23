package it.uniroma2.eu.bookcycle.bean;


public class PropostaBean extends PropostaParzialeBean{

    private String usernameDestinatario;
    private long idLibroD;

    @Override
    public boolean completo() {
        return super.completo()
                && usernameDestinatario != null
                && idLibroD != 0;
    }


    public String getDestinatario() {

        return usernameDestinatario;
    }

    public void setDestinatario(String usernameDestinatario) {
        this.usernameDestinatario = usernameDestinatario;
    }



    public long getLibroRichiesto() {
        return idLibroD;
    }

    public void setLibroRichiesto(long idLibroD) {
        this.idLibroD = idLibroD;
    }
}
