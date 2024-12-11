import apiClient from "../apiClient";

/**
 *
 * @param id :invoiceId
 * @returns : INVOICE {
 * id:string
 * products:[]
 * deliveryInfo:{}
 * subtotal:number
 * deliveryFee:number
 * VAT:number
 * total:number
 * }
 */
export const getInvoiceApi = async (id: string) => {
  try {
    const response = await apiClient.get(`/api/invoice/get/${id}`);
    return response.data;
  } catch (error) {
    console.error("Error getting invoice:", error);
    throw error;
  }
};
