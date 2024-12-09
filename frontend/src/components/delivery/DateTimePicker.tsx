import React from "react";
import Select from "./Select.tsx";
interface DeliveryDateTimePickerProps {
  value: any;
  onChange: (field: string, value: any) => void;
}

const DeliveryDateTimePicker = ({
  value,
  onChange,
}: DeliveryDateTimePickerProps) => {
  const [day, setDay] = React.useState<string>(() => {
    if (value) {
      const date = value.split(" ")[1];
      return date;
    } else {
      return "";
    }
  });
  const [time, setTime] = React.useState<string>(() => {
    if (value) {
      const date = value.split(" ")[0];
      return date;
    } else {
      return "";
    }
  });
  const [selected, setSelected] = React.useState(time || null);
  React.useEffect(() => {
    onChange("time", `${time} ${day}`);
    console.log(value);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [time, day]);

  // Generate time slots (every 30 minutes)
  const generateTimeSlots = React.useMemo(() => {
    const slots: string[] = [];
    for (let hour = 0; hour < 24; hour++) {
      for (let minute of [0, 30]) {
        const timeString = `${hour.toString().padStart(2, "0")}:${minute
          .toString()
          .padStart(2, "0")}`;
        slots.push(timeString);
      }
    }
    return slots;
  }, []);

  // Get today's date in YYYY-MM-DD format for min attribute
  const getTodayString = () => {
    const today = new Date();
    return today.toISOString().split("T")[0];
  };

  const handleChangeTime = (value) => {
    setSelected(value);
    setTime(value.value);
  };

  return (
    <div className="flex gap-6 flex-wrap">
      {/* Date Input */}
      <div className="flex-1">
        <input
          type="date"
          id="delivery-date"
          min={getTodayString()}
          value={day || ""}
          onChange={(e) => setDay(e.target.value)}
          className="mt-1 p-2 w-full border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
          required
        />
      </div>

      {/* Time Input */}
      <div className="flex-1">
        <Select
          label=""
          value={selected}
          onChange={handleChangeTime}
          options={generateTimeSlots.map((timeSlot) => ({
            label: timeSlot,
            value: timeSlot,
          }))}
        />
      </div>
    </div>
  );
};

export default DeliveryDateTimePicker;
