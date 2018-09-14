package TeoriaTP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class RunLenghtCoding {
	private ArrayList<Integer> codificacion;
	
	RunLenghtCoding(){
		this.codificacion = new ArrayList<Integer>();
	}

	//CantFilas 1 byte, CantColumnas 1 byte, Nombre 1 byte y el numero 1 byte
	public void comprimir(int[][] matrizImagen){
		int tonoActual = -1;
		int contadorApariciones = 0;
		this.codificacion.add(matrizImagen.length);
		this.codificacion.add(matrizImagen[0].length);


		for (int fil = 0; fil < matrizImagen.length; fil++) {
			for (int col = 0; col < matrizImagen[0].length; col++){
				if(matrizImagen[fil][col] == tonoActual){
					contadorApariciones++;
				}else{
					if(tonoActual != -1){
						this.codificacion.add(tonoActual);
						this.codificacion.add(contadorApariciones);
					}
					tonoActual = matrizImagen[fil][col];
					contadorApariciones = 1;
				}
				if(fil == matrizImagen.length-1 & col == matrizImagen[0].length-1){
					this.codificacion.add(tonoActual);
					this.codificacion.add(contadorApariciones);	
				}
			}	
		}
	}
	
	public boolean guardarCompresion() throws IOException{
		Archivo archivoGuardar = new Archivo();
		OutputStream outsSt = archivoGuardar.guardar();
		if(outsSt != null){
				  DataOutputStream archivoOutput = new DataOutputStream(outsSt);
				  for(Integer intg: this.codificacion){
					  archivoOutput.writeInt(intg);
				  }
				  archivoOutput.close();
				  return true;
		}
			
		return false;
		
	}
		
	public void descomprimir(DataInputStream archivo) throws IOException{
	
		int cantFilas = archivo.readInt();
		int cantColumnas = archivo.readInt();
		int[][] matrizImagen = new int[cantFilas][cantColumnas];
		int simbolo = archivo.readInt(); 
		int contador = archivo.readInt(); 
		for (int fil = 0; fil < cantFilas; fil++) {
			for (int col = 0; col < cantColumnas; col++){
				if(contador > 0){
					matrizImagen[fil][col] = simbolo;
					contador--;
				}else{
					simbolo = archivo.readInt(); 
					contador = archivo.readInt(); 
					matrizImagen[fil][col] = simbolo;
					contador--;
				}
			}
		}	
		Imagen imagenNueva = new Imagen();
		imagenNueva.generarImagen(matrizImagen);
		imagenNueva.guardar();
	}
}
