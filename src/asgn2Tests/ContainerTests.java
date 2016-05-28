package asgn2Tests;

/* Some valid container codes used in the tests below:
 * INKU2633836
 * KOCU8090115
 * MSCU6639871
 * CSQU3054389
 */

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import asgn2Codes.ContainerCode;
import asgn2Containers.DangerousGoodsContainer;
import asgn2Containers.FreightContainer;
import asgn2Containers.GeneralGoodsContainer;
import asgn2Containers.RefrigeratedContainer;
import asgn2Exceptions.InvalidCodeException;
import asgn2Exceptions.InvalidContainerException;


public class ContainerTests {
	//Implementation Here - includes tests for ContainerCode and for the actual container classes. 

	//  {{ Tests For Container Constructor
	@Test
	public void containerConstructorTest(){
		
		//Valid Container Code
		boolean success = true;
		try {
			new asgn2Codes.ContainerCode("ABCU1234564");
		} catch (InvalidCodeException e){
			System.err.println("Failure at Container Code Success: " + e.getMessage());
			success = false;
		}
		
		//Invalid Container Code Length
		success = true;
		try {
			new asgn2Codes.ContainerCode("ABCU123456");
		} catch (InvalidCodeException e){
			success = false;
			if (e.getMessage() != "Container Code is Wrong Length")
			{
				System.err.println("Fail at Container Code Length: " + e.getMessage());
			}
		}
		if (success) {
			System.err.println("Fail at Container Code Length, No Err");
		}

		//Invalid Container Code Owner Name
		success = true;
		try {
			new asgn2Codes.ContainerCode("a5+U1234564");
		} catch (InvalidCodeException e){
			success = false;
			if (e.getMessage() != "Owner Code is not capitals"){
				System.err.println("Fail at Container Code Owner Name: " + e.getMessage());
			}
		}
		if (success) {
			System.err.println("Fail at Container Code Owner, No Err");
		}

		//Invalid Container Code Type
		success = true;
		try {
			new asgn2Codes.ContainerCode("ABCf1234564");
		} catch (InvalidCodeException e){
			success = false;
			if (e.getMessage() != "Type is not freight (U)")
			{
				System.err.println("Fail at Container Code Type: " + e.getMessage());
			}
		}
		if (success) {
			System.err.println("Fail at Container Code Type, No Err");
		}

		//Invalid Container Code Serial
		success = true;
		try {
			new asgn2Codes.ContainerCode("ABCU123hue4");
		} catch (InvalidCodeException e){
			success = false;
			if (e.getMessage() != "Serial is not all numbers")
			{
				System.err.println("Fail at Container Code Serial: " + e.getMessage());
			}
		}
		if (success) {
			System.err.println("Fail at Container Code Serial, No Err");
		}
		
		//Invalid Container Code Check
		success = true;
		try {
			new asgn2Codes.ContainerCode("ABCU1234566");
		} catch (InvalidCodeException e){
			success = false;
			if (e.getMessage() != "Invalid check value")
			{
				System.err.println("Fail at Container Code Check Value: " + e.getMessage());
			}
		}
		if (success) {
			System.err.println("Fail at Container Code Check Value, No Err");
		}
	}
	//  }}	
	
	// {{ Tests for FreightContainer Class
	@Test
	public void containerGeneralTest(){
		
		//Valid General Container Constructor && Methods
		boolean success = true;
		ContainerCode testContainerCode = null;
		GeneralGoodsContainer testGenGoods = null;
		
		try { 
			testContainerCode = new ContainerCode("ABCU1234564");
			testGenGoods = new GeneralGoodsContainer(testContainerCode, 15);
		} catch (InvalidContainerException e){
			System.err.println("Failure at Valid General Container Success: " + e.getMessage());
			success = false;
		} catch (InvalidCodeException b){
			System.err.println("Failure at Valid General Container Success: " + b.getMessage());
			success = false;
		}
		if (!((success) && (testGenGoods.getCode() == testContainerCode) && (testGenGoods.getGrossWeight() == 15))){
			System.err.println("Failure at Valid General Container Constructor");
		}
		
		
		//Invalid General Container: Out of weight range
		try { 
			new asgn2Containers.GeneralGoodsContainer(new ContainerCode("ABCU1234564"), 3);
		} catch (InvalidContainerException e){
			if (e.getMessage() != "Container not within weight limits (4 - 30)"){
				System.err.println("Failure At General Goods: Out of Weight: " + e.getMessage());
			}
		} catch (InvalidCodeException e){
			System.err.println("Failure At General Goods: Out of Weight, bad contCode");
		}
		
		
		//Valid Refrigerated Container Constructor && Methods
		success = true;
		RefrigeratedContainer testRefrigContainer = null;
		
		try { 
			testRefrigContainer = new RefrigeratedContainer(new ContainerCode("ABCU1234564"), 15, 65);
		} catch (InvalidContainerException e){
			System.err.println(e.getMessage());
			success = false;
		} catch (InvalidCodeException e){
			System.err.println(e.getMessage());
			success = false;
		}
		if (!((success) && (testRefrigContainer.getTemperature() == 65))){
			System.err.println("Failure at valid Refrigerated Container Constructor");
		}
		
		//Valid Dangerous Container Constructor && Methods
		success = true;
		DangerousGoodsContainer testDangerContainer = null;
		
		try { 
			testDangerContainer = new DangerousGoodsContainer(new ContainerCode("ABCU1234564"), 15, 8);
		} catch (InvalidContainerException e){
			System.err.println(e.getMessage());
			success = false;
		} catch (InvalidCodeException e){
			System.err.println(e.getMessage());
			success = false;
		}
		if (!((success) && (testDangerContainer.getCategory() == 8))){
			System.err.println("Failure at valid Refrigerated Container Constructor");
		}
		
		
		//Invalid Danger Container: Out of Danger range
		try { 
			new asgn2Containers.DangerousGoodsContainer(new ContainerCode("ABCU1234564"), 15, 10);
		} catch (InvalidContainerException e){
			if (e.getMessage() != "Invalid Danger Category"){
				System.err.println("Failure At General Goods: Out of Weight: " + e.getMessage());
			}
		} catch (InvalidCodeException e){
			System.err.println("Failure At General Goods: Out of Weight, bad contCode");
		}
		
	}
		
	// }}
	
}

	
	
