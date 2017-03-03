package com.searchRequests;

import com.travixTest.SupplierType;

/**
 *  Use factory pattern for getting the corresponding supplier according to the input parameter
 *  returns the proper supplier as a Supplier so that for the higher level internal 
 *  implementations are transparent
 * 
 * @author Lucia
 */
public class SupplierFactory {
		
	/**Getting the appropiate supplier
	 * 
	 * @param supplier
	 * @return
	 */
	   public Supplier getSupplier(SupplierType supplier){
	      if(supplier == null){
	         return null;
	      }		
	      if(supplier == SupplierType.CRAZY_AIR){
	         return new CrazyAirSupplier();
	         
	      } else if(supplier == SupplierType.TOUGH_JET){
	         return new ToughJetSupplier();	         
	      }
	      
	      return null;
	   }
		   
}
