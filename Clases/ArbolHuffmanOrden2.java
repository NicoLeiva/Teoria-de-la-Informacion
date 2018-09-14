package TeoriaTP;

import java.util.ArrayList;


import TeoriaTP.ArbolHuffman;

public class ArbolHuffmanOrden2 extends ArbolHuffman{
	float[][] matrizCond = null;
	
	public ArbolHuffmanOrden2(){
		super();
	}
	
	public ArbolHuffmanOrden2(int[][] matrizImagen) {
		obtenerFrecuencias(matrizImagen);
		this.asignarIndices();
		this.matrizCond = new float [l.size()][l.size()];
		this.contarAparicionesOrden2(matrizImagen);
		this.calcularProbCondicional();
		generarNodosHuffman();
		generarCodificacion();
		obtenerCodificacion();
	}
	
	public void generarNodosHuffman(){
		
		ArrayList<NodoArbolHuffman> listaNodosArbolNuevo = new ArrayList<NodoArbolHuffman>();
		for (Tono primerTono : l) {
			String nombrePrimerTono = primerTono.getNombre();
			int codPrimerTono = primerTono.getCodigo();
			for (Tono segundoTono : l) {
				String nombreSegundoTono = segundoTono.getNombre();
				int codSegundoTono = segundoTono.getCodigo();
				//ACA ESTABA EL ERROR

				float frecNodoAgregar = matrizCond[codSegundoTono][codPrimerTono]*primerTono.getFrecuencia();
				String nombreNodoAgregar = nombrePrimerTono+"-"+nombreSegundoTono;
				int codigoNodoAgregar = primerTono.getCodigo();
				NodoArbolHuffman nodoAgregar = new NodoArbolHuffman(nombreNodoAgregar ,codigoNodoAgregar, frecNodoAgregar);
				listaNodosArbolNuevo.add(nodoAgregar);
			}
		}
		this.listaNodos=listaNodosArbolNuevo;
	}
	

	
	public void asignarIndices(){
		for(int valorInd=0;valorInd<l.size();valorInd++){
			this.l.get(valorInd).setCodigo(valorInd);
		}
	}
	
	public void contarAparicionesOrden2(int[][] matriz){
		for(int fil = 0; fil < matriz.length; fil++){
			if(matriz[0].length % 2 != 0){
				if(fil % 2 == 0){
					for (int col = 0; col < matriz[0].length-1; col=col+2){
						int vCol = obtenerValorIndiceTono(matriz[fil][col]);
						int vFil = obtenerValorIndiceTono(matriz[fil][col+1]);
						if(vCol != -1 && vFil != -1){
							this.matrizCond[vFil][vCol]++;
						}

					}
					if(fil == matriz.length-1){
						int vCol = obtenerValorIndiceTono(matriz[fil][matriz[0].length-1]);
						if(vCol != -1){
							this.matrizCond[vCol][vCol]++;
						}	
					}else{
						int vCol = obtenerValorIndiceTono(matriz[fil][matriz[0].length-1]);
						int vFil = obtenerValorIndiceTono(matriz[fil+1][0]);
						if(vCol != -1 && vFil != -1){
							this.matrizCond[vFil][vCol]++;
						}
					}
				}else{
					for (int col = 1; col < matriz[0].length; col=col+2){
						int vCol = obtenerValorIndiceTono(matriz[fil][col]);
						int vFil = obtenerValorIndiceTono(matriz[fil][col+1]);
						if(vCol != -1 && vFil != -1){
							this.matrizCond[vFil][vCol]++;
						}
					}
				}
			}else{
				for (int col = 0; col < matriz[0].length-1; col=col+2){
					int vCol = obtenerValorIndiceTono(matriz[fil][col]);
					int vFil = obtenerValorIndiceTono(matriz[fil][col+1]);
					if(vCol != -1 && vFil != -1){
						this.matrizCond[vFil][vCol]++;
					}
				}
			}
		}
	}
		
	public int obtenerValorIndiceTono(int nomTono){
		for (Tono t : l) {
			if(t.getNombre().equals(String.valueOf(nomTono))){
				return t.getCodigo();
			}
		}
		return -1;
	}
	
	public void calcularProbCondicional(){
		float totalcol=0f;
		for (int col = 0; col < matrizCond[0].length; col++) {
			for (int fil = 0; fil < matrizCond.length; fil++) {
				totalcol+= matrizCond[fil][col];
			}
			for (int fil = 0; fil < matrizCond[0].length; fil++) {
				matrizCond[fil][col]= matrizCond[fil][col]/totalcol;
			}
		totalcol=0;
		}
	
	}
		
	public float calcularLongitudMedia(){
		float longMedia=0f;
		for(NodoArbolHuffman nodo : listaNodos){
			longMedia += nodo.getFrecuencia()*nodo.getLongitud();
		}
		return longMedia/2;
	}
		

	public float calcularEntropia(){
		float entropia=0f;
		for(Tono tono : l){
			float auxsuma = 0f;
			float[][] matrizCondicional = this.getMatrizCondicional();
			for(int fil = 0; fil < matrizCondicional.length; fil++){
				if(matrizCondicional[fil][tono.getCodigo()] != 0){
					auxsuma += -(matrizCondicional[fil][tono.getCodigo()]*(Math.log(matrizCondicional[fil][tono.getCodigo()])/Math.log(2)));
					}
			}
			entropia += (auxsuma*tono.getFrecuencia());
		}
		
		return entropia;
	}
	
	
	public float[][] getMatrizCondicional(){
		return this.matrizCond;
	}
	
	
}