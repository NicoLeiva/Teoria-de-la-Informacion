package TeoriaTP;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class Canal {
	private float[][] matrizCanal;
	private float[][] matrizApariciones;
	private ArrayList <Tono> listaTonos;
	float cantTonosEnviados;
	
	
	public Canal(){
		this.listaTonos = new ArrayList <Tono>();
	}
	
	public float[][] getMatrizCanal(){
		return this.matrizCanal;
	}
	public void setCantidadTonosEnviados(float cant){
		this.cantTonosEnviados = cant;
	}
	
	public void setTonos(ArrayList <Tono> lista){
		this.listaTonos = lista;
	}
	
	public ArrayList <Tono> getTonos(){
		return this.listaTonos;
	}
	
	public boolean existeTono(int tono){
		for (Tono t : this.listaTonos) {
			if(t.getNombre().equals(Integer.toString(tono))){
				return true;
			}
		}
	return false;
	}
	
	public void addTono(String n,int c){
		Tono t = new Tono(n,c);
		this.listaTonos.add(t);
	}
	
	public int getCodigoTono(int tono){
		for(Tono t: this.listaTonos){
			if(t.getNombre().equals(Integer.toString(tono))){
				return t.getCodigo();
			}
		}
		return -1;
	}
	
	public String getNombreTono(int codTono){
		for(Tono t: this.listaTonos){
			if(t.getCodigo() == codTono){
				return t.getNombre();
			}
		}
		return Integer.toString(-1);
	}
	
	public float getFrecuenciaSalidaTono(int codTono){
		for(Tono t: this.listaTonos){
			if(t.getCodigo() == codTono){
				return t.getFrecuencia();
			}
		}
		return -1;
	}
	
	public float getFrecuenciaLlegadaTono(int codTono){
		for(Tono t: this.listaTonos){
			if(t.getCodigo() == codTono){
				return t.getFrecuenciaLlegada();
			}
		}
		return -1;
	}

	public void aumentarAparicionTono(int tono){
		for(Tono t: this.listaTonos){
			if(t.getNombre().equals(Integer.toString(tono))){
				t.aumentarApariciones();
			}
		}
	}
	
	public void calcularFrecuenciasSalida(int cantEnvios){
		for(Tono t: this.listaTonos){
			float frec = (float) t.getApariciones()/cantEnvios;
			t.setFrecuencia(frec);
		}
	}
	public void aumentarAparicionLlegadaTono(int tono){
		for(Tono t: this.listaTonos){
			if(t.getNombre().equals(Integer.toString(tono))){
				t.aumentarAparicionesLlegada();
			}
		}
	}
	
	public void calcularFrecuenciasLlegada(int cantEnvios){
		for(Tono t: this.listaTonos){
			float frec = (float) t.getFrecuenciaLlegada()/cantEnvios;
			t.setFrecuenciaLlegada(frec);
		}
	}

	
	public void generarCanal(Imagen imagenOriginal, Imagen imagenTransmitida){
		imagenOriginal.levantarImagen();
		imagenTransmitida.levantarImagen();
		
		int cantFilas = imagenOriginal.getMatriz().length;
		int cantCol = imagenOriginal.getMatriz()[0].length;
		
		this.cantTonosEnviados = imagenOriginal.getMatriz().length * imagenOriginal.getMatriz()[0].length;
		
		int codigoTono = 0;
		for(int fil = 0; fil<cantFilas; fil++){
			for(int col = 0; col<cantCol; col++){
				int tonoSalida = imagenOriginal.getMatriz()[fil][col];
				if(!this.existeTono(tonoSalida)){
					this.addTono(Integer.toString(tonoSalida),codigoTono);
					codigoTono++;
				}else{
					this.aumentarAparicionTono(tonoSalida);
				}	
			}
		}
		
		for(int fil = 0; fil<cantFilas; fil++){
			for(int col = 0; col<cantCol; col++){
				int tonoSalida = imagenTransmitida.getMatriz()[fil][col];
					this.aumentarAparicionLlegadaTono(tonoSalida);
			}
		}

		this.calcularFrecuenciasSalida((int)this.cantTonosEnviados);
		this.calcularFrecuenciasLlegada((int)this.cantTonosEnviados);
		
		this.matrizApariciones = new float[this.listaTonos.size()][this.listaTonos.size()];	
		
		
		for(int fil = 0; fil<cantFilas; fil++){
			for(int col = 0; col<cantCol; col++){
				int tonoSalida = imagenOriginal.getMatriz()[fil][col];
				int tonoLlegada = imagenTransmitida.getMatriz()[fil][col];
				
				int codTonoSalida = this.getCodigoTono(tonoSalida);
				int codTonoLlegada = this.getCodigoTono(tonoLlegada);
				this.matrizApariciones[codTonoLlegada][codTonoSalida] ++;
			}
		}
		
		this.matrizCanal = this.calcularMatrizCondicionalYdadoX();
	}
	

	//Calcula la matriz condicional (Y/X)
	public float[][] calcularMatrizCondicionalYdadoX(){
		float[][] matrizCondicional = new float[this.matrizApariciones.length][this.matrizApariciones.length];
		for(int col = 0; col < this.matrizApariciones.length; col++){
			float probabilidadX = 0;
			for(int fil = 0; fil < this.matrizApariciones.length; fil++){
				probabilidadX += this.matrizApariciones[fil][col];
			}
			
			for(int fil = 0; fil < this.matrizApariciones.length; fil++){
				matrizCondicional[fil][col] = this.matrizApariciones[fil][col] / probabilidadX;
			}

		}
		return matrizCondicional; 
		
	}
	
	
	public float calcularRuido(){
		float ruido = 0;
		for(int col = 0; col < this.matrizCanal.length; col++){
			float probabilidadX = this.getFrecuenciaSalidaTono(col);
		
			float entropiaYdadoX = 0;
			for(int fil = 0; fil < this.matrizCanal.length; fil++){
				//Sumatoria -p(y/x)*log(p(y/x))
				if(this.matrizCanal[fil][col] > 0f){
					entropiaYdadoX += -this.matrizCanal[fil][col]*Math.log(this.matrizCanal[fil][col])/Math.log(2);
				}
			}
			
			ruido += entropiaYdadoX * probabilidadX;
		}
		return ruido;	
	}
	

	//Calcula la matriz condicional (X/Y)
	public float[][] calcularMatrizCondicionalXdadoY(){
		float[][] matrizCondicional = new float[this.matrizApariciones.length][this.matrizApariciones.length];
		for(int fil = 0; fil < this.matrizApariciones.length; fil++){
			float probabilidadY = 0;
			for(int col = 0; col < this.matrizApariciones.length; col++){
				probabilidadY += this.matrizApariciones[fil][col];
			}
			
		
			for(int col = 0; col < this.matrizApariciones.length; col++){
				if(probabilidadY != 0){
					matrizCondicional[fil][col] = this.matrizApariciones[fil][col] / probabilidadY;
					}
			}

		}
		return matrizCondicional;
	}

	
	
	public float calcularPerdida(){
		float perdida = 0;
		for(int fil = 0; fil < this.matrizCanal.length; fil++){
			float probabilidadY = this.getFrecuenciaLlegadaTono(fil);

			float entropiaXdadoY = 0;
			for(int col = 0; col < this.matrizCanal.length; col++){
				//Sumatoria p(y)
				float pxy = (this.matrizCanal[fil][col]*this.getFrecuenciaSalidaTono(col))/probabilidadY;
				//Sumatoria -p(x/y)*log(p(x/y))
				if(pxy > 0f){
					entropiaXdadoY += -pxy*(Math.log(pxy)/Math.log(2));
				}

			}
			perdida += entropiaXdadoY * probabilidadY;
		}
		return perdida;	
	}
	
	
	
	
	public float calcularEntropiaSalida(){
		float entropiaDeSalida = 0;
		float probabilidadX = 0;
		for(Tono t: this.listaTonos){
			probabilidadX = t.getFrecuencia();
			if(probabilidadX > 0f){
				entropiaDeSalida += -probabilidadX*Math.log(probabilidadX)/Math.log(2);
			}
		}
		return entropiaDeSalida;
	}
	
	
	public float calcularInformacionMutua(){
		return (this.calcularEntropiaSalida() - this.calcularPerdida());
	}
	
	
	/*
	public float calcularEntropiaLlegada(){
		float entropiaDeLlegada = 0;
		float probabilidadY = 0;
		for(Tono t: this.listaTonos){
			probabilidadY = t.getFrecuenciaLlegada();
			if(probabilidadY > 0f){
				entropiaDeLlegada += -probabilidadY*Math.log(probabilidadY)/Math.log(2);
			}
		}
		return entropiaDeLlegada;
	}
	
	
	public float calcularInformacionMutua(){
		return (this.calcularEntropiaLlegada() - this.calcularRuido());
	}
	*/
	public float[][] calcularmatrizProbabilidadYdadoXAcumulada(){
		float[][] matrizProbabilidadYdadoXAcumulada = new float[this.matrizCanal.length][this.matrizCanal.length];
		for(int col = 0; col<this.matrizCanal.length; col++){
			float acumulado = 0;
			for(int fil = 0; fil<this.matrizCanal.length; fil++){
				acumulado += this.matrizCanal[fil][col];
				matrizProbabilidadYdadoXAcumulada[fil][col] = acumulado;
			}
		}
		return matrizProbabilidadYdadoXAcumulada;
	}
	
	public Imagen enviarImagen(Imagen imagenEnviar){
		imagenEnviar.levantarImagen();
		
		int cantFilas = imagenEnviar.getMatriz().length;
		int cantCol = imagenEnviar.getMatriz()[0].length;
		
		int[][] matrizImagenLlegada = new int[cantFilas][cantCol];
		
		float[][] matrizProbabilidadYdadoXAcumulada = this.calcularmatrizProbabilidadYdadoXAcumulada();
		
		for(int fil = 0; fil<cantFilas; fil++){
			for(int col = 0; col<cantCol; col++){
				int tonoSalida = imagenEnviar.getMatriz()[fil][col];
				
				//Aparece un tono que no estaba
				if(tonoSalida == 156){
					tonoSalida = 167;
				}
				
				String tonoLlegada = this.getNombreTono(this.obtenerTonoLlegada(tonoSalida, matrizProbabilidadYdadoXAcumulada));
			
				matrizImagenLlegada[fil][col] = Integer.parseInt(tonoLlegada);
				}
			}
		
		Imagen imgGenerar = new Imagen();
		imgGenerar.generarImagen(matrizImagenLlegada);
		
		return imgGenerar;
		
	}
	
	public int obtenerTonoSalida(float[] probAcum){
		Random rnd = new Random();
		float random1 = rnd.nextFloat();
		for(int i=0;i<probAcum.length;i++){
			if(random1 < probAcum[i]){
				return i;
			}
		}
		return -1;
	}
	
	public int obtenerTonoLlegada(int tonoSalida,float[][] probAcum){
		Random rnd = new Random();
		float random1 = rnd.nextFloat();
		for(int i=0;i<probAcum.length;i++){
			if(random1 < probAcum[i][this.getCodigoTono(tonoSalida)]){
				return i;
			}
		}
		return -1;
	}
	
	
	public void analizarCanalConImagenEnviada(Imagen imagenOriginal, Imagen imagenEnviad, ArrayList<Tono> listaTonosOriginal){
		imagenOriginal.levantarImagen();
		imagenEnviad.levantarImagen();
		
		int cantFilas = imagenOriginal.getMatriz().length;
		int cantCol = imagenOriginal.getMatriz()[0].length;
		
		this.cantTonosEnviados = imagenOriginal.getMatriz().length * imagenOriginal.getMatriz()[0].length;
		
		this.listaTonos = listaTonosOriginal;
		
		this.matrizApariciones = new float[this.listaTonos.size()][this.listaTonos.size()];	
		
	
		for(int fil = 0; fil<cantFilas; fil++){
			for(int col = 0; col<cantCol; col++){
				int tonoSalida = imagenOriginal.getMatriz()[fil][col];
				int tonoLlegada = imagenEnviad.getMatriz()[fil][col];
				
				int codTonoSalida = this.getCodigoTono(tonoSalida);
				int codTonoLlegada = this.getCodigoTono(tonoLlegada);
				
				
				this.matrizApariciones[codTonoLlegada][codTonoSalida] ++;
			}
		}	
		
		
		this.matrizCanal = this.calcularMatrizCondicionalYdadoX();
		
	}
	
	
	
	public NodoInformacion enviarDatosAleatorios(int cantidad){
		ArrayList<Integer> listaSalida = new ArrayList<Integer>();
		ArrayList<Integer> listaLlegada = new ArrayList<Integer>();
		float[] probAcum = new float[this.listaTonos.size()];
		float acumulado = 0;
		for(int i=0; i<this.listaTonos.size();i++){
			probAcum[i] = 0;
		}
		for(int i=0; i<this.listaTonos.size();i++){
			acumulado += this.listaTonos.get(i).getFrecuencia();
			probAcum[i] = acumulado;
		}
		
		int emitidos = 1; 
		String tonoSalida = this.getNombreTono(0);
		String tonoLlegada = this.getNombreTono(0);
		
		float[][] matrizProbabilidadYdadoXAcumulada = this.calcularmatrizProbabilidadYdadoXAcumulada();

		while(emitidos < cantidad+1){
		
			tonoSalida = this.getNombreTono(this.obtenerTonoSalida(probAcum));
			tonoLlegada = this.getNombreTono(this.obtenerTonoLlegada(Integer.parseInt(tonoSalida), matrizProbabilidadYdadoXAcumulada));
			
			listaSalida.add(Integer.parseInt(tonoSalida));
			listaLlegada.add(Integer.parseInt(tonoLlegada));

			emitidos++;
		}
		
		Canal nuevoCanal = new Canal();
		nuevoCanal.setCantidadTonosEnviados(cantidad);
		nuevoCanal.setTonos(this.listaTonos);
		nuevoCanal.analizarListas(listaSalida,listaLlegada);
		float ruido = nuevoCanal.calcularRuido();
		float perdida = nuevoCanal.calcularPerdida();
		float informacionMutua = nuevoCanal.calcularInformacionMutua();
		
		
		NodoInformacion nodoResult = new NodoInformacion(cantidad, ruido, perdida, informacionMutua);
		
		return nodoResult;
	}
	
	public void analizarListas(ArrayList<Integer> listaSal,ArrayList<Integer> listaLle){
		this.matrizApariciones = new float[this.listaTonos.size()][this.listaTonos.size()];
		
		for(int indice = 0; indice<listaSal.size(); indice++){
			int tonoSalida = listaSal.get(indice);
			int tonoLlegada = listaLle.get(indice);

			int codTonoSalida = this.getCodigoTono(tonoSalida);
			int codTonoLlegada = this.getCodigoTono(tonoLlegada);
			this.matrizApariciones[codTonoLlegada][codTonoSalida] ++;
		}
	
		this.matrizCanal = this.calcularMatrizCondicionalYdadoX();
	}
	
	public String mostarMatrizCanal(){
		DecimalFormat df = new DecimalFormat("0.000");
		String resultado = "";
		int codigo =0;
		resultado += "         ";
		while(codigo < this.listaTonos.size()){
			int cantSimbolos = this.getNombreTono(codigo).length();
			while (cantSimbolos < 4){
				resultado += "  ";
				cantSimbolos++;
			}
			resultado += this.getNombreTono(codigo);
			while (cantSimbolos < 11){
				resultado += " ";
				cantSimbolos++;
				}
			codigo++;
		}
		resultado += "\r\n";
		for(int fil=0;fil<this.matrizCanal.length;fil++){
			int cantSimbolos1 = this.getNombreTono(fil).length();
			while (cantSimbolos1 < 3){
				resultado += "  ";
				cantSimbolos1++;
			}
			resultado += this.getNombreTono(fil);
			resultado += "     ";
			
			for(int col=0;col<this.matrizCanal.length;col++){
				resultado += df.format(this.matrizCanal[fil][col]);
				resultado += "     ";
			}
			resultado += "\r\n\r\n";
		}
		return resultado;
	}
}
