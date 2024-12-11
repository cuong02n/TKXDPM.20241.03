import apiClient from "../apiClient";

/**
 *
 * @param orderId
 * @param deliveryInfoId
 * @returns invoiceId: string
 */
export const createInvoiceApi = async (
  orderId: string,
  deliveryInfoId: string
) => {
  try {
    const response = await apiClient.post(`/api/invoice/create/`, {
      orderId,
      deliveryInfoId,
    });
    return response.data;
  } catch (error) {
    console.error("Error creating invoice:", error);
    throw error;
  }
};
