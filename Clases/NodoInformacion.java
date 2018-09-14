package TeoriaTP;

public class NodoInformacion {
	int cantidad;
	float ruido;
	float perdida;
	float informacionMutua;


public NodoInformacion(){
	this.cantidad = 0;
	this.ruido = 0;
	this.perdida = 0;
	this.informacionMutua = 0;
}

public NodoInformacion(int cant, float ruid, float perdid, float info){
	this.cantidad = cant;
	this.ruido = ruid;
	this.perdida = perdid;
	this.informacionMutua = info;
}

public int getCantidadDatosEnviados(){
	return this.cantidad;
}

public float getRuido(){
	return this.ruido;
}

public float getPerdida(){
	return this.perdida;
}

public float getInformacionMutua(){
	return this.informacionMutua;
}


}