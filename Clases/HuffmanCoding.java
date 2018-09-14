package TeoriaTP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;


public class HuffmanCoding {
	ArrayList<String> encabezadoNombres;
	ArrayList<Integer> encabezadoFrecuencias;
	ArrayList<Integer> codificacionBits;
	int cFilas;
	int cCol;
	
	
	public HuffmanCoding(){
		this.encabezadoNombres = new ArrayList<String>();
		this.encabezadoFrecuencias = new ArrayList<Integer>();
		this.codificacionBits = new ArrayList<Integer>();
	}
	
	public void comprimir(int[][] matrizImagen, ArbolHuffman codificacionHuffman){
		for(Tono nodo: codificacionHuffman.getTonos()){
			this.encabezadoNombres.add(nodo.getNombre());
			this.encabezadoFrecuencias.add(nodo.getApariciones());
		}
		
		this.cFilas = matrizImagen.length;
		this.cCol = matrizImagen[0].length;
		
		int bitsGuardar = 0;
		int cantDigitos = 0;
		
		for (int fil = 0; fil < matrizImagen.length; fil++) {
			for (int col = 0; col < matrizImagen[0].length; col++){
				for(NodoArbolHuffman nodo: codificacionHuffman.getNodos()){
					if(nodo.getSimboloId() == matrizImagen[fil][col]){
						String codificacionNodo = nodo.getCodificacion();
						int longitud = nodo.getLongitud();
						for (int i = 0; i < longitud; i++){
							bitsGuardar = bitsGuardar << 1;
							if(codificacionNodo.charAt(i) == '1'){
								bitsGuardar = bitsGuardar | 1;
							}
							cantDigitos++;
							if(cantDigitos == 16){
								this.codificacionBits.add(bitsGuardar);
								bitsGuardar = 0;
								cantDigitos = 0;
							}
						}
					}
				}
			}
		}
		if((cantDigitos < 16)&(cantDigitos != 0)){
				bitsGuardar = bitsGuardar << (16 - cantDigitos);
				this.codificacionBits.add(bitsGuardar);
		}

	}
	
	public boolean guardarCompresion() throws IOException{
		Archivo archivoGuardar = new Archivo();
		OutputStream outsSt = archivoGuardar.guardar();
			if(outsSt != null){
			  DataOutputStream archivoOutput = new DataOutputStream(outsSt);
			  archivoOutput.writeInt(this.cFilas);
			  archivoOutput.writeInt(this.cCol);
			  archivoOutput.writeInt(this.encabezadoNombres.size());

			  for(int i=0;i<this.encabezadoNombres.size();i++){
				  archivoOutput.writeInt(Integer.valueOf(this.encabezadoNombres.get(i)));
				  archivoOutput.writeInt(this.encabezadoFrecuencias.get(i));
			  }
			  
			  for(int intg: this.codificacionBits){
				  archivoOutput.writeInt(intg);
			  }
			  archivoOutput.close();
			  return true;
			}
		return false;
	}
	
	public void descomprimir(DataInputStream archivo) throws IOException{
		try {
			int cantFilas = archivo.readInt();
			int cantColumnas = archivo.readInt();
	        
			int[][] matrizImagen = new int[cantFilas][cantColumnas];

			int cantNodos = archivo.readInt();
	        
	        ArrayList<NodoArbolHuffman> listaNodos = new ArrayList<NodoArbolHuffman>();
	        
	        for(int i=0;i<cantNodos;i++){
	        	int enteroLeido = archivo.readInt();
	        	String nombreNodo = Integer.toString(enteroLeido);
	        	enteroLeido = archivo.readInt();
	        	float frecuenciaNodo = (float)enteroLeido/(cantFilas*cantColumnas);
	        	NodoArbolHuffman nodoAgregar = new NodoArbolHuffman(nombreNodo, Integer.valueOf(nombreNodo), frecuenciaNodo);
	        	listaNodos.add(nodoAgregar);
	        }
	        
	        ArbolHuffman arbol = new ArbolHuffman(listaNodos);
	        
	        NodoArbolHuffman nodoActual = arbol.getRaiz();


	        int mascara = 1 << 15;
	        
	        int intLeido = archivo.readInt();
	        int cantDigitosLeidos = 0;
	        
			for (int fil = 0; fil < cantFilas; fil++) {
				for (int col = 0; col < cantColumnas; col++){
					while(!nodoActual.esHoja()){
						if(cantDigitosLeidos < 16){
							if((intLeido & mascara) == 32768){
								nodoActual = nodoActual.getHijoIzquierdo();
							}else{
								nodoActual = nodoActual.getHijoDerecho();
							}
							intLeido = intLeido << 1;
							cantDigitosLeidos++;
						}else{
							intLeido = archivo.readInt();
							cantDigitosLeidos = 0;
						}
					}
					matrizImagen[fil][col] = nodoActual.getSimboloId();
					nodoActual = arbol.getRaiz();
				}
			}        
			Imagen imagenNueva = new Imagen();
			imagenNueva.generarImagen(matrizImagen);	
			imagenNueva.guardar();
		} catch (EOFException e) {
		}	
	}
}
