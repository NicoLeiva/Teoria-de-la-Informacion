package TeoriaTP;

public class Tono {
	private String nombre;
	private int codigo;
	private int apariciones;
	private float frecuencia;
	private float frecuenciaLlegada;
	
	public Tono(String n,int c,int cod){
		setNombre(n);
		codigo=cod;
		setApariciones(1);
		frecuencia=0f;
		frecuenciaLlegada=0f;
	}
	
	public Tono(String n,int c){
		setNombre(n);
		codigo=c;
		setApariciones(1);
		frecuencia=0f;
		frecuenciaLlegada=0f;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public float getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(float frecuencia) {
		this.frecuencia = frecuencia;
	}
	
	public float getFrecuenciaLlegada() {
		return frecuenciaLlegada;
	}

	public void setFrecuenciaLlegada(float frecuencia) {
		this.frecuenciaLlegada = frecuencia;
	}
	
	public void aumentarApariciones(){
		apariciones++;
	}
	
	public void aumentarAparicionesLlegada(){
		frecuenciaLlegada++;
	}
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getApariciones() {
		return apariciones;
	}

	public void setApariciones(int apariciones) {
		this.apariciones = apariciones;
	}

}
