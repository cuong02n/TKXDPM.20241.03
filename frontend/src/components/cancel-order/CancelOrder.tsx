import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../store/store";
import { useOneOrder } from "../hooks/useOneOrder.ts";
import { setInitialInvoice } from "../store/invoiceSlice.ts";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Modal from "@mui/material/Modal";
import apiClient from "../../api/apiClient.ts";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  border: "2px solid #880000",
  boxShadow: 24,
  p: 4,
  borderRadius: 8,
};

const CancelOrder = ({ orderId }: { orderId?: number }) => {
  const navigate = useNavigate();
  const invoice = useSelector((state: RootState) => state.invoice);
  const { resetOrder } = useOneOrder();
  const dispatch = useDispatch<AppDispatch>();
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const handleDeleteOrder = async () => {
    try {
      const res = await apiClient.delete(
        `/order/delete?orderId=${orderId ? orderId : invoice.orderId}`
      );
      if (res && res.status === 200) {
        console.log("ORDER CANCELED", res);
        toast.success("Order canceled successfully");
        resetOrder();
        dispatch(setInitialInvoice);
        handleClose();
        navigate("/");
      }
    } catch (err) {
      console.log("ERROR CANCELING ORDER", err);
      toast.error("Error canceling order");
    }
  };

  return (
    <div>
      <button
        onClick={handleOpen}
        className="w-48 mt-4 bg-rose-600 text-white py-3 rounded-lg hover:bg-gradient-to-r from-rose-900 to-rose-800 transition-colors duration-300 font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-rose-500 focus:ring-offset-2 active:scale-[0.99] disabled:opacity-50 disabled:cursor-not-allowed"
      >
        <span className="text-xl drop-shadow-md">Cancel order</span>
      </button>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Typography
            className="text-red-600"
            id="modal-modal-title"
            variant="h6"
            component="h2"
          >
            Confirm delete this order
          </Typography>
          <Typography id="modal-modal-description" sx={{ mt: 2 }}>
            Once canceled, the order will be deleted and you will not be able to
            recover this order
          </Typography>
          <div className="flex justify-center">
            <button
              onClick={handleDeleteOrder}
              className="w-48 mt-4 bg-rose-600 text-white py-3 rounded-lg hover:bg-gradient-to-r from-rose-900 to-rose-800 transition-colors duration-300 font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-rose-500 focus:ring-offset-2 active:scale-[0.99] disabled:opacity-50 disabled:cursor-not-allowed"
            >
              Confirm cancel
            </button>
          </div>
        </Box>
      </Modal>
    </div>
  );
};

export default CancelOrder;
