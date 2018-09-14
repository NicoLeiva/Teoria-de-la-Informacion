package TeoriaTP;


public class NodoArbolHuffman {
	private String nombre;
	private int simbolo;
	private NodoArbolHuffman hijoDerecho, hijoIzquierdo;
	private float frecuencia;
	private String codificacion;
	private int longitud;


public NodoArbolHuffman(){
		this.nombre="";
		this.simbolo = -1;
		this.hijoDerecho = null;
		this.hijoIzquierdo = null;
		this.frecuencia = 0f;
		this.codificacion = "";
		this.longitud = 0;
	}

public NodoArbolHuffman(String n,int identificacion, float frec){
	this.nombre=n;
	this.simbolo=identificacion;
	this.hijoDerecho=null;
	this.hijoIzquierdo=null;
	this.frecuencia = frec;
	this.codificacion = "";
	this.longitud = 0;
}

public NodoArbolHuffman(String n,int identificacion, NodoArbolHuffman hIzq, NodoArbolHuffman hDer, float frec){
	this.nombre=n;
	this.simbolo=identificacion;
	this.hijoDerecho=hDer;
	this.hijoIzquierdo=hIzq;
	this.frecuencia = frec;
	this.codificacion = "";
	this.longitud = 0;
}

public void setHijoDerecho(NodoArbolHuffman hDer){
	this.hijoDerecho = hDer;
}

public void setHijoIzquierdo(NodoArbolHuffman hIzq){
	this.hijoDerecho = hIzq;
}

public NodoArbolHuffman getHijoDerecho(){
	return this.hijoDerecho;
}

public NodoArbolHuffman getHijoIzquierdo(){
	return this.hijoIzquierdo;
}

public void setFrecuencia(int frec){
	this.frecuencia = frec;
}

public float getFrecuencia(){
	return this.frecuencia;
}

public void setSimboloId(int id){
	this.simbolo = id;
}

public int getSimboloId(){
	return this.simbolo;
}

public String getNombre() {
	return nombre;
}

public void setNombre(String nombre) {
	this.nombre = nombre;
}

public void setCodificacion(String cod){
	this.codificacion = cod;
}

public String getCodificacion(){
	return this.codificacion;
}

public void setLongitud(int longit){
	this.longitud = longit;
}

public int getLongitud(){
	return this.longitud;
}

public boolean esHoja(){
	if(this.hijoIzquierdo == null && this.hijoDerecho == null){
		return true;
	}
	return false;
}

}



