import apiClient from "../apiClient";

/**
 *
 * @param invoiceId
 * @param total
 * @returns redirectUrl: string
 */
export const payOrderApi = async (invoiceId: string, total: number) => {
  try {
    const response = await apiClient.post(`/api/payment/pay`, {
      invoiceId,
      total,
    });
    return response.data;
  } catch (error) {
    console.error("Error paying order:", error);
    throw error;
  }
};
