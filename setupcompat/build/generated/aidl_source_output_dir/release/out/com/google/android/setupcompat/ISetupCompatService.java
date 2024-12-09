/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.google.android.setupcompat;
/** Declares the interface for compat related service methods. */
public interface ISetupCompatService extends android.os.IInterface
{
  /** Default implementation for ISetupCompatService. */
  public static class Default implements com.google.android.setupcompat.ISetupCompatService
  {
    /** Notifies SetupWizard that the screen is using PartnerCustomizationLayout */
    @Override public void validateActivity(java.lang.String screenName, android.os.Bundle arguments) throws android.os.RemoteException
    {
    }
    @Override public void logMetric(int metricType, android.os.Bundle arguments, android.os.Bundle extras) throws android.os.RemoteException
    {
    }
    @Override public void onFocusStatusChanged(android.os.Bundle bundle) throws android.os.RemoteException
    {
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements com.google.android.setupcompat.ISetupCompatService
  {
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.google.android.setupcompat.ISetupCompatService interface,
     * generating a proxy if needed.
     */
    public static com.google.android.setupcompat.ISetupCompatService asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof com.google.android.setupcompat.ISetupCompatService))) {
        return ((com.google.android.setupcompat.ISetupCompatService)iin);
      }
      return new com.google.android.setupcompat.ISetupCompatService.Stub.Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
      return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
      java.lang.String descriptor = DESCRIPTOR;
      if (code >= android.os.IBinder.FIRST_CALL_TRANSACTION && code <= android.os.IBinder.LAST_CALL_TRANSACTION) {
        data.enforceInterface(descriptor);
      }
      switch (code)
      {
        case INTERFACE_TRANSACTION:
        {
          reply.writeString(descriptor);
          return true;
        }
      }
      switch (code)
      {
        case TRANSACTION_validateActivity:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          android.os.Bundle _arg1;
          _arg1 = _Parcel.readTypedObject(data, android.os.Bundle.CREATOR);
          this.validateActivity(_arg0, _arg1);
          break;
        }
        case TRANSACTION_logMetric:
        {
          int _arg0;
          _arg0 = data.readInt();
          android.os.Bundle _arg1;
          _arg1 = _Parcel.readTypedObject(data, android.os.Bundle.CREATOR);
          android.os.Bundle _arg2;
          _arg2 = _Parcel.readTypedObject(data, android.os.Bundle.CREATOR);
          this.logMetric(_arg0, _arg1, _arg2);
          break;
        }
        case TRANSACTION_onFocusStatusChanged:
        {
          android.os.Bundle _arg0;
          _arg0 = _Parcel.readTypedObject(data, android.os.Bundle.CREATOR);
          this.onFocusStatusChanged(_arg0);
          break;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
      return true;
    }
    private static class Proxy implements com.google.android.setupcompat.ISetupCompatService
    {
      private android.os.IBinder mRemote;
      Proxy(android.os.IBinder remote)
      {
        mRemote = remote;
      }
      @Override public android.os.IBinder asBinder()
      {
        return mRemote;
      }
      public java.lang.String getInterfaceDescriptor()
      {
        return DESCRIPTOR;
      }
      /** Notifies SetupWizard that the screen is using PartnerCustomizationLayout */
      @Override public void validateActivity(java.lang.String screenName, android.os.Bundle arguments) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(screenName);
          _Parcel.writeTypedObject(_data, arguments, 0);
          boolean _status = mRemote.transact(Stub.TRANSACTION_validateActivity, _data, null, android.os.IBinder.FLAG_ONEWAY);
        }
        finally {
          _data.recycle();
        }
      }
      @Override public void logMetric(int metricType, android.os.Bundle arguments, android.os.Bundle extras) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(metricType);
          _Parcel.writeTypedObject(_data, arguments, 0);
          _Parcel.writeTypedObject(_data, extras, 0);
          boolean _status = mRemote.transact(Stub.TRANSACTION_logMetric, _data, null, android.os.IBinder.FLAG_ONEWAY);
        }
        finally {
          _data.recycle();
        }
      }
      @Override public void onFocusStatusChanged(android.os.Bundle bundle) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _Parcel.writeTypedObject(_data, bundle, 0);
          boolean _status = mRemote.transact(Stub.TRANSACTION_onFocusStatusChanged, _data, null, android.os.IBinder.FLAG_ONEWAY);
        }
        finally {
          _data.recycle();
        }
      }
    }
    static final int TRANSACTION_validateActivity = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_logMetric = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_onFocusStatusChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
  }
  public static final java.lang.String DESCRIPTOR = "com.google.android.setupcompat.ISetupCompatService";
  /** Notifies SetupWizard that the screen is using PartnerCustomizationLayout */
  public void validateActivity(java.lang.String screenName, android.os.Bundle arguments) throws android.os.RemoteException;
  public void logMetric(int metricType, android.os.Bundle arguments, android.os.Bundle extras) throws android.os.RemoteException;
  public void onFocusStatusChanged(android.os.Bundle bundle) throws android.os.RemoteException;
  /** @hide */
  static class _Parcel {
    static private <T> T readTypedObject(
        android.os.Parcel parcel,
        android.os.Parcelable.Creator<T> c) {
      if (parcel.readInt() != 0) {
          return c.createFromParcel(parcel);
      } else {
          return null;
      }
    }
    static private <T extends android.os.Parcelable> void writeTypedObject(
        android.os.Parcel parcel, T value, int parcelableFlags) {
      if (value != null) {
        parcel.writeInt(1);
        value.writeToParcel(parcel, parcelableFlags);
      } else {
        parcel.writeInt(0);
      }
    }
  }
}
