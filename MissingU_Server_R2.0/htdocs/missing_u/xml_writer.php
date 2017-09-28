<?php 

class SimpleXmlWriter 
{ 
    var $xml; 

    function SimpleXmlWriter() 
    { 
        
    } 
    
    function Start($element)
    {
    	$buf = sprintf('<%s>', $element);
    	
    	$this->xml .= $buf;
    }
    
    function Add($element, $text)
    {    	
    	$buf = sprintf('<%s>%s</%s>', $element, $text, $element);
    	
    	$this->xml .= $buf;    	
    }
    
    function End($element)
    {
    	$buf = sprintf('</%s>', $element);
    	
    	$this->xml .= $buf;
    	
    }

    function GetXml() 
    { 
        return $this->xml; 
    } 
} 
?>