package org.modelmapper;

import org.modelmapper.spi.MappingContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.modelmapper.Conditions.*;
import static org.modelmapper.Conditions.isNotNull;
import static org.modelmapper.Conditions.isNull;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author James Bassett
 */
public class ConditionsTest {

    private MappingContext mappingContext;

    @BeforeMethod
    public void setUp() {
        this.mappingContext = mock(MappingContext.class);
    }

    @Test
    public void testIsNullWithNull() throws Exception {
        when(this.mappingContext.getSource()).thenReturn(null);
        assertTrue(isNull().applies(this.mappingContext));
        verify(this.mappingContext).getSource();
    }

    @Test
    public void testIsNullWithValue() throws Exception {
        when(this.mappingContext.getSource()).thenReturn("value");
        assertFalse(isNull().applies(this.mappingContext));
        verify(this.mappingContext).getSource();
    }

    @Test
    public void testIsNotNullWithNull() throws Exception {
        when(this.mappingContext.getSource()).thenReturn(null);
        assertFalse(isNotNull().applies(this.mappingContext));
        verify(this.mappingContext).getSource();
    }

    @Test
    public void testIsNotNullWithValue() throws Exception {
        when(this.mappingContext.getSource()).thenReturn("value");
        assertTrue(isNotNull().applies(this.mappingContext));
        verify(this.mappingContext).getSource();
    }

    @Test
    public void testIsDestinationNullWithNull() throws Exception {
        when(this.mappingContext.getDestination()).thenReturn(null);
        assertTrue(isDestinationNull().applies(this.mappingContext));
        verify(this.mappingContext).getDestination();
    }

    @Test
    public void testIsDestinationNullWithValue() throws Exception {
        when(this.mappingContext.getDestination()).thenReturn("value");
        assertFalse(isDestinationNull().applies(this.mappingContext));
        verify(this.mappingContext).getDestination();
    }

    @Test
    public void testIsDestinationNotNullWithNull() throws Exception {
        when(this.mappingContext.getDestination()).thenReturn(null);
        assertFalse(isDestinationNotNull().applies(this.mappingContext));
        verify(this.mappingContext).getDestination();
    }

    @Test
    public void testIsDestinationNotNullWithValue() throws Exception {
        when(this.mappingContext.getDestination()).thenReturn("value");
        assertTrue(isDestinationNotNull().applies(this.mappingContext));
        verify(this.mappingContext).getDestination();
    }

    @Test
    public void testIsTypeWithSameType() throws Exception {
        when(this.mappingContext.getSourceType()).thenReturn(String.class);
        assertTrue(isType(String.class).applies(this.mappingContext));
        verify(this.mappingContext).getSourceType();
    }

    @Test
    public void testIsTypeWithDifferentType() throws Exception {
        when(this.mappingContext.getSourceType()).thenReturn(Integer.class);
        assertFalse(isType(String.class).applies(this.mappingContext));
        verify(this.mappingContext).getSourceType();
    }

    @Test
    public void testIsDestinationTypeWithSameType() throws Exception {
        when(this.mappingContext.getDestinationType()).thenReturn(String.class);
        assertTrue(isDestinationType(String.class).applies(this.mappingContext));
        verify(this.mappingContext).getDestinationType();
    }

    @Test
    public void testIsDestinationTypeWithDifferentType() throws Exception {
        when(this.mappingContext.getDestinationType()).thenReturn(Integer.class);
        assertFalse(isDestinationType(String.class).applies(this.mappingContext));
        verify(this.mappingContext).getDestinationType();
    }

    @Test
    public void testNotWithTrue() throws Exception {
        when(this.mappingContext.getSource()).thenReturn("value");
        assertTrue(not(isNull()).applies(this.mappingContext));
        verify(this.mappingContext).getSource();
    }

    @Test
    public void testNotWithFalse() throws Exception {
        when(this.mappingContext.getSource()).thenReturn(null);
        assertFalse(not(isNull()).applies(this.mappingContext));
        verify(this.mappingContext).getSource();
    }

    @Test
    public void testAndWithTrue() throws Exception {
        when(this.mappingContext.getSource()).thenReturn(null);
        when(this.mappingContext.getDestination()).thenReturn(null);

        // TODO casting should not be required!
        assertTrue(and((Condition<String, String>) isNull(), (Condition<String, String>) isDestinationNull()).applies(this.mappingContext));

        verify(this.mappingContext).getSource();
        verify(this.mappingContext).getDestination();
    }

    @Test
    public void testAndWithFalse() throws Exception {
        when(this.mappingContext.getSource()).thenReturn(null);
        when(this.mappingContext.getDestination()).thenReturn("value");

        // TODO casting should not be required!
        assertFalse(and((Condition<String, String>) isNull(), (Condition<String, String>) isDestinationNull()).applies(this.mappingContext));

        verify(this.mappingContext).getSource();
        verify(this.mappingContext).getDestination();
    }

    @Test
    public void testOrWithTrue() throws Exception {
        when(this.mappingContext.getSource()).thenReturn(null);
        when(this.mappingContext.getDestination()).thenReturn("value");

        // TODO casting should not be required!
        assertTrue(or((Condition<String, String>) isNull(), (Condition<String, String>) isDestinationNull()).applies(this.mappingContext));

        verify(this.mappingContext).getSource();
        verify(this.mappingContext, never()).getDestination();
    }

    @Test
    public void testOrWithFalse() throws Exception {
        when(this.mappingContext.getSource()).thenReturn("value");
        when(this.mappingContext.getDestination()).thenReturn("value");

        // TODO casting should not be required!
        assertFalse(or((Condition<String, String>) isNull(), (Condition<String, String>) isDestinationNull()).applies(this.mappingContext));

        verify(this.mappingContext).getSource();
        verify(this.mappingContext).getDestination();
    }
}